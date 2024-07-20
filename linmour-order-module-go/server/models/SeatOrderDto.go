package models

type SeatOrderDto struct {
	ID        string      `json:"id"`
	PayAmount float64     `json:"payAmount"`
	TableId   string      `json:"tableId"`
	List      []OrderItem `json:"list"`
}
