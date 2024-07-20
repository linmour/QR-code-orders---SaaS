package models

import (
	"encoding/json"
	"strconv"
)

// OrderInfo 代表订单信息的结构体
type OrderInfo struct {
	ID          string  `json:"id" gorm:"column:id"`                    // 订单ID
	Openid      string  `json:"openid" gorm:"column:openid"`            // 用户ID
	TableId     int64   `json:"tableId" gorm:"column:table_id"`         // 桌号ID
	PayType     int32   `json:"payType" gorm:"column:pay_type"`         // 支付方式
	PayStatus   int32   `json:"payStatus" gorm:"column:pay_status"`     // 支付状态
	PayAmount   float64 `json:"payAmount" gorm:"column:pay_amount"`     // 支付金额
	OrderStatus int32   `json:"orderStatus" gorm:"column:order_status"` // 订单状态
	Remark      string  `json:"remark" gorm:"column:remark"`            // 备注
	ShopId      int64   `json:"shopId" gorm:"column:shop_id"`           // 商店ID
	CreateTime  string  `json:"createTime" gorm:"column:create_time"`   // 创建时间
	UpdateTime  string  `json:"updateTime" gorm:"column:update_time"`   // 更新时间
	CreateBy    string  `json:"createBy" gorm:"column:create_by"`       // 创建人
	UpdateBy    string  `json:"updateBy" gorm:"column:update_by"`       // 更新人
	Deleted     string  `json:"deleted" gorm:"column:deleted"`          // 是否删除
}

// UnmarshalJSON 自定义JSON解码逻辑
func (o *OrderInfo) UnmarshalJSON(data []byte) error {
	// 创建一个中间结构体来接收JSON数据
	type intermediate struct {
		ID          string `json:"id"`
		Openid      string `json:"openid"`
		TableId     string `json:"tableId"`
		PayType     string `json:"payType"`
		PayStatus   string `json:"payStatus"`
		PayAmount   string `json:"payAmount"`
		OrderStatus string `json:"orderStatus"`
		Remark      string `json:"remark"`
		ShopId      string `json:"shopId"`
		CreateTime  string `json:"createTime"`
		UpdateTime  string `json:"updateTime"`
		CreateBy    string `json:"createBy"`
		UpdateBy    string `json:"updateBy"`
		Deleted     string `json:"deleted"`
	}

	var jsonMap intermediate
	err := json.Unmarshal(data, &jsonMap)
	if err != nil {
		return err
	}

	// 手动转换字段类型
	o.ID = jsonMap.ID
	o.Openid = jsonMap.Openid
	o.TableId, err = strconv.ParseInt(jsonMap.TableId, 10, 64)
	if err != nil {
		return err
	}
	payTypeInt, err := strconv.ParseInt(jsonMap.PayType, 10, 32)
	if err != nil {
	}
	// 显式转换为 int32 并赋值给 o.PayType
	o.PayType = int32(payTypeInt)

	// 解析 PayStatus 字段
	payStatusInt, err := strconv.ParseInt(jsonMap.PayStatus, 10, 32)
	if err != nil {
	}
	// 显式转换为 int32 并赋值给 o.PayStatus
	o.PayStatus = int32(payStatusInt)
	o.PayAmount, err = strconv.ParseFloat(jsonMap.PayAmount, 64)
	if err != nil {
		return err
	}
	orderStatus, err := strconv.ParseInt(jsonMap.OrderStatus, 10, 32)
	if err != nil {
		// 设置默认的 OrderStatus，例如 0
		o.OrderStatus = int32(0)
	} else {
		o.OrderStatus = int32(orderStatus)
	}
	o.Remark = jsonMap.Remark
	o.ShopId, err = strconv.ParseInt(jsonMap.ShopId, 10, 64)
	if err != nil {
		return err
	}
	o.CreateTime = jsonMap.CreateTime
	o.UpdateTime = jsonMap.UpdateTime

	o.CreateBy = jsonMap.CreateBy
	o.UpdateBy = jsonMap.UpdateBy
	o.Deleted = jsonMap.Deleted

	return nil
}
