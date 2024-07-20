package models

type OrderInfoPage struct {
	PayType int   `json:"payType"`
	ShopId  int64 `json:"shopId"`
	// 可能还需要添加分页相关的字段，例如页码和每页数量等
	Page     int `json:"page"`
	PageSize int `json:"pageSize"`
}
