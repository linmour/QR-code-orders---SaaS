package models

type CreateOrderDto struct {
	OrderId    string      `json:"orderId"`
	TableId    int64       `json:"tableId"`
	Amount     float64     `json:"amount"`
	OrderItems []OrderItem `json:"orderItems"`
	Remark     string      `json:"remark"`
	ShopId     int64       `json:"shopId"`
}
