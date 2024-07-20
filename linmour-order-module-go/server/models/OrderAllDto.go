package models

type OrderAllDto struct {
	OrderInfo  OrderInfo   `json:"orderInfo"`
	OrderItems []OrderItem `json:"orderItems"`
}
