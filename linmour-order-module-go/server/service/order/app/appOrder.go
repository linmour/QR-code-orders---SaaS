package app

import (
	"encoding/json"
	"fmt"
	"github.com/google/uuid"
	"github.com/mitchellh/mapstructure"
	"github.com/redis/go-redis/v9"
	"log"
	"server/global"
	"server/models"
	pb "server/proto"
	"server/utils"
	"strconv"
	"sync"
	"time"
)

type AppOrderService struct {
}

func (OrderService *AppOrderService) CreateOrder(createOrderDto *models.CreateOrderDto) *models.Result {
	if len(createOrderDto.OrderItems) == 0 {
		return models.Error("订单条目不能为空")
	}

	orderInfo := &models.OrderInfo{}
	orderId := ""
	tableId := strconv.FormatInt(createOrderDto.TableId, 10)
	shopId := strconv.FormatInt(createOrderDto.ShopId, 10)
	orderInfoKey := "orderInfo:" + shopId + ":" + tableId
	orderItemKey := "orderItem:" + shopId + ":" + tableId

	// 检查Redis中是否存在本桌订单信息
	if len(utils.HGetAll(orderInfoKey)) == 0 {
		orderId = (uuid.New()).String()
		orderInfo.ID = orderId
		orderInfo.TableId = createOrderDto.TableId
		orderInfo.PayAmount = createOrderDto.Amount
		orderInfo.ShopId = createOrderDto.ShopId
		orderInfo.Remark = createOrderDto.Remark
		orderInfo.CreateTime = (time.Now()).String()

		orderInfoMap := make(map[string]interface{})

		mapstructure.Decode(orderInfo, &orderInfoMap)
		utils.SetHashValues(orderInfoKey, orderInfoMap)
	} else {
		orderId := utils.GetHashValue(orderInfoKey, "id").(*redis.StringCmd).Val()

		// 更新订单信息
		utils.SetHashValue(orderInfoKey, "remark", createOrderDto.Remark)
		a, _ := utils.GetHashValue(orderInfoKey, "payAmount").(*redis.StringCmd).Float64()
		utils.SetHashValue(orderInfoKey, "payAmount", createOrderDto.Amount+a)
		orderInfo.ID = orderId
		orderInfo.TableId, _ = utils.GetHashValue(orderInfoKey, "tableId").(*redis.StringCmd).Int64()
		orderInfo.PayAmount, _ = utils.GetHashValue(orderInfoKey, "payAmount").(*redis.StringCmd).Float64()
		orderInfo.ShopId = 1
		orderInfo.Remark = utils.GetHashValue(orderInfoKey, "remark").(*redis.StringCmd).Val()
	}

	createOrderDto.OrderId = orderId
	orderItems := createOrderDto.OrderItems
	// 扣减库存
	productIds := make([]int64, len(orderItems))
	for i, item := range orderItems {
		productIds[i] = item.ProductID
	}
	longList := &pb.LongList{
		Values: productIds,
	}
	global.ProductClient.ReduceInventories(longList)
	var seatOrderDto models.SeatOrderDto
	seatOrderDto.ID = orderInfo.ID
	seatOrderDto.PayAmount = orderInfo.PayAmount

	result := &models.Result{Data: seatOrderDto}
	utils.SetCacheList(orderItemKey, orderItems)
	return result
}

func (OrderService *AppOrderService) Checkout(checkoutDto *models.CheckoutDto) *models.Result {
	tableId := checkoutDto.TableId
	orderInfoKey := "orderInfo" + "1" + tableId
	orderItemKey := "orderItem" + "1" + tableId
	var mu sync.Mutex
	mu.Lock() // 加锁
	utils.SetHashValue(orderInfoKey, "payType", checkoutDto.PayType)
	utils.SetHashValue(orderInfoKey, "payStatus", 1)

	if checkoutDto.PayType != 4 {
		//把结账人的openid存给订单，是一种妥协，因为这样只有结账的人能看见订单了
		utils.SetHashValue(orderInfoKey, "openid", checkoutDto.Openid)

	}
	map1 := utils.GetAllHash(orderInfoKey)

	if map1 == nil {
		return models.Success(nil)
	}

	orderInfo := models.OrderInfo{}
	mapstructure.Decode(map1, &orderInfo)
	orderInfo.Openid = checkoutDto.Openid
	global.DB.Table("order_info").Create(orderInfo)
	cacheList, err := utils.GetCacheList[models.OrderItem](orderItemKey)
	if err != nil {
		log.Fatalf("Error getting cache list: %v", err)
	}

	fmt.Printf("Cache List: %+v\n", cacheList)

	for _, m := range cacheList {
		m.OrderID = orderInfo.ID // 注意：需要确保OrderInfo有ID字段
		m.Status = 1
	}

	global.DB.Table("order_item").Create(&cacheList)

	utils.DeleteObject(orderInfoKey)
	utils.DeleteObject(orderItemKey)
	return models.Success(nil)

}

func (OrderService *AppOrderService) GetHistoryOrderList(tableId string) ([]models.OrderAllDto, error) {
	var orderInfos []models.OrderInfo
	// 查询本桌所有未支付的订单
	if err := global.DB.Where("table_id = ? AND pay_status = ?", tableId, 1).Find(&orderInfos).Error; err != nil {
		log.Println("Error fetching orderInfos:", err)
		return nil, err
	}

	var orderAllDtos []models.OrderAllDto
	for _, m := range orderInfos {
		var orderItems []models.OrderItem
		// 查询每个订单的菜品详情
		global.DB.Where("order_id = ?", m.ID).Find(&orderItems)
		var collect []string

		for _, item := range orderItems {
			collect = append(collect, item.ID) // 假设OrderItem有一个GetId()方法来获取ID
		}

		//TODO   Result result = productFeign.getProductDetails(collect);

		// 这里省略了模拟远程调用及数据转换的部分，直接使用查询到的orderItems
		orderAllDto := models.OrderAllDto{
			OrderInfo:  m,
			OrderItems: orderItems,
		}
		orderAllDtos = append(orderAllDtos, orderAllDto)
	}

	// 反转列表
	for i, j := 0, len(orderAllDtos)-1; i < j; i, j = i+1, j-1 {
		orderAllDtos[i], orderAllDtos[j] = orderAllDtos[j], orderAllDtos[i]
	}

	return orderAllDtos, nil
}

func (OrderService *AppOrderService) GetCurrentOrderInfo(tableId int64) models.OrderAllDto {
	var orderAllDto models.OrderAllDto

	orderInfoKey := "orderInfo:" + "1" + strconv.FormatInt(tableId, 10)
	orderItemKey := "orderItem:" + "1" + strconv.FormatInt(tableId, 10)

	map1 := utils.GetAllHash(orderInfoKey)

	s, _ := json.Marshal(map1)
	var orderInfo models.OrderInfo
	json.Unmarshal(s, &orderInfo)

	cacheList := utils.LRange(orderItemKey, 0, -1)

	var orderItems []models.OrderItem
	for _, itemStr := range cacheList {
		var item models.OrderItem
		err := json.Unmarshal([]byte(itemStr), &item)
		if err != nil {
			// 处理错误
		}
		orderItems = append(orderItems, item)
	}

	orderAllDto.OrderInfo = orderInfo
	orderAllDto.OrderItems = orderItems
	return orderAllDto
}

func (OrderService *AppOrderService) GetOrderInfoDetail(orderId uint) (*models.OrderAllDto, error) {
	var orderInfo models.OrderInfo
	var orderItems []models.OrderItem

	// 查询订单详情
	result := global.DB.First(&orderInfo, orderId)
	if result.Error != nil {
		return nil, result.Error
	}

	// 查询订单项详情
	result = global.DB.Where("order_id = ?", orderId).Find(&orderItems)
	if result.Error != nil {
		return nil, result.Error
	}

	orderAllDto := &models.OrderAllDto{
		OrderInfo:  orderInfo,
		OrderItems: orderItems,
	}

	return orderAllDto, nil
}

func (OrderService *AppOrderService) UpdateOrderItemStatus(tableId string, index int64) error {
	orderItemKey := "orderItem:" + "1" + tableId

	obj := utils.GetListValue(orderItemKey, index)

	var orderItem models.OrderItem
	json.Unmarshal(obj.([]byte), &orderItem)

	// 切换状态
	orderItem.Status = 1 - orderItem.Status // 简化切换逻辑

	// 序列化OrderItem为JSON字符串再存回Redis
	itemBytes, err := json.Marshal(orderItem)
	if err != nil {
		return err
	}

	utils.SetListValue(orderItemKey, index, string(itemBytes))

	return nil
}
