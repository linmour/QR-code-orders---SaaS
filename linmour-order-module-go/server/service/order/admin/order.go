package admin

import (
	"encoding/json"
	"fmt"
	"github.com/mitchellh/mapstructure"
	"math"
	"server/global"
	"server/models"
	"server/utils"
	"strings"
)

type OrderService struct {
}

func (OrderService *OrderService) A() (Order *OrderService, err error) {
	// 待实现 TODO...
	fmt.Println("Go is a great language")
	return nil, nil
}

func (OrderService *OrderService) GetOrderByShopId() (*models.Result, error) {
	var orderInfos []models.OrderInfo
	result := global.DB.Table("order_info").Where("shop_id = ?", 1).Find(&orderInfos)
	if result.Error != nil {
		return nil, result.Error
	}

	// 提取订单ID
	var orderIds []string
	for _, orderInfo := range orderInfos {
		orderIds = append(orderIds, string(orderInfo.ID))
	}

	var orderItems []models.OrderItem
	global.DB.Where("order_id IN ?", orderIds).Find(&orderItems)

	// 将OrderItem与OrderInfo关联
	orderAllDtos := make([]models.OrderAllDto, len(orderInfos))
	for i, orderInfo := range orderInfos {
		orderAllDtos[i].OrderInfo = orderInfo
		orderAllDtos[i].OrderItems = filterOrderItemsByOrderId(orderItems, string(orderInfo.ID))
	}
	return models.Success(orderAllDtos), nil
}

func (OrderService *OrderService) GetCurrentOrderInfo(tableId string) (*models.Result, error) {

	orderAllDto := &models.OrderAllDto{}

	orderInfoKey := "orderInfo:" + "1:2"
	orderItemKey := "orderItem:" + "1:2"

	// 获取订单信息
	mapData := utils.HGetAll(orderInfoKey)

	if len(mapData) == 0 {
		return models.Success(orderAllDto), nil
	}
	cleanedMapData := make(map[string]string) // 创建一个新的 map 来存储清理后的结果
	// 假设 mapData 是 map[string]string 类型
	for key, value := range mapData {
		// 直接处理 value，它是一个 interface{} 类型
		// 使用 strings.Trim 去除前后的双引号
		cleanedValue := strings.Trim(value, "\"")
		if value == "" {
			delete(mapData, key)
		}
		// 更新 map 中的值
		mapData[key] = cleanedValue
		if cleanedValue != "" {
			cleanedMapData[key] = cleanedValue
		}
	}

	// 现在 mapData 已经被清理，可以安全地序列化
	orderInfoJSON, err := json.Marshal(cleanedMapData)
	if err != nil {
		// 处理序列化错误
		return nil, err
	}
	var orderInfo models.OrderInfo
	err = json.Unmarshal([]byte(orderInfoJSON), &orderInfo)
	if err != nil {
		return nil, err
	}

	orderAllDto.OrderInfo = orderInfo

	// 获取订单项
	cacheList := utils.LRange(orderItemKey, 0, -1)

	var orderItems []models.OrderItem

	// 遍历 cacheList 中的每个 JSON 字符串
	for _, itemJSON := range cacheList {
		// 为每个 JSON 字符串创建一个 OrderItem 对象
		var orderItem models.OrderItem

		// 反序列化单个 JSON 字符串到 OrderItem 对象
		err := json.Unmarshal([]byte(itemJSON), &orderItem)
		if err != nil {
			// 处理单个 JSON 字符串的反序列化错误
			return nil, err
		}

		// 将反序列化得到的 OrderItem 对象添加到 orderItems 切片中
		orderItems = append(orderItems, orderItem)
	}

	orderAllDto.OrderItems = orderItems

	return models.Success(orderAllDto), nil
}

func (OrderService *OrderService) GetOrderPayAmount(orderInfoPage models.OrderInfoPage) (*models.Result, error) {
	// 创建查询构建器
	var orderInfos []models.OrderInfo

	query := global.DB.Table("order_info")

	if orderInfoPage.PayType != 0 {
		query = query.Where("pay_type = ?", orderInfoPage.PayType)
	}
	if orderInfoPage.ShopId != 0 {
		query = query.Where("shop_id = ?", orderInfoPage.ShopId)
	}

	// 执行查询
	result := query.Find(&orderInfos)
	if result.Error != nil {
		return nil, result.Error
	}

	// 转换 OrderInfo 列表到 OrderInfoDto 列表
	var orderInfoDtos []models.OrderInfoDto
	// 使用mapstructure进行转换
	for _, orderInfo := range orderInfos {
		var orderInfoDto models.OrderInfoDto
		err := mapstructure.Decode(orderInfo, &orderInfoDto)
		if err != nil {
			fmt.Println("Error during decoding:", err)
			continue
		}
		orderInfoDtos = append(orderInfoDtos, orderInfoDto)
	}
	// 获取商店 ID 列表
	var shopIds []int64
	for _, orderInfo := range orderInfos {
		shopIds = append(shopIds, orderInfo.ShopId)
	}

	// 查询商店信息
	var shops []models.Shop
	if len(shopIds) > 0 {
		result := global.DB.Model(&models.Shop{}).Where("id IN (?)", shopIds).Find(&shops)
		if result.Error != nil {
			return nil, result.Error
		}
	}

	// 为 OrderInfoDto 设置商店佣金和名称
	for _, dto := range orderInfoDtos {
		for _, shop := range shops {
			if dto.ShopId == shop.ID {
				// 计算佣金
				payAmount := dto.PayAmount // 假设info.PayAmount是float64类型

				// 计算佣金比率
				commission := payAmount * (shop.FeeRate / 100.0)

				// 四舍五入到最接近的整数
				dto.Commission = math.Round(commission)

				dto.ShopName = shop.Name
				break
			}
		}
	}

	return models.Success(orderInfoDtos), nil
}

// filterOrderItemsByOrderId 过滤属于特定订单ID的订单项
func filterOrderItemsByOrderId(orderItems []models.OrderItem, orderId string) []models.OrderItem {
	var filtered []models.OrderItem
	for _, item := range orderItems {
		if item.OrderID == orderId {
			filtered = append(filtered, item)
		}
	}
	return filtered
}

func (OrderService *OrderService) ChangeOrderStatus(params map[string]interface{}) (*models.Result, error) {
	//var orderInfos []models.OrderInfo
	//result := global.DB.Table("order_info").Where("shop_id = ?", 1).Find(&orderInfos)
	//if result.Error != nil {
	//	return nil, result.Error
	//}
	//
	//// 提取订单ID
	//var orderIds []string
	//for _, orderInfo := range orderInfos {
	//	orderIds = append(orderIds, string(orderInfo.ID))
	//}
	//
	//var orderItems []models.OrderItem
	//global.DB.Where("order_id IN ?", orderIds).Find(&orderItems)
	//
	//// 将OrderItem与OrderInfo关联
	//orderAllDtos := make([]models.OrderAllDto, len(orderInfos))
	//for i, orderInfo := range orderInfos {
	//	orderAllDtos[i].OrderInfo = orderInfo
	//	orderAllDtos[i].OrderItems = filterOrderItemsByOrderId(orderItems, string(orderInfo.ID))
	//}
	stu := models.OrderInfo{}
	mapstructure.Decode(params, &stu)
	fmt.Println(stu)

	global.DB.Table("order_info").Where("id = ?", params["id"]).Updates(stu)
	return models.Success("orderAllDtos"), nil

}
