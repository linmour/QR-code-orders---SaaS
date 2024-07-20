package models

import "time"

type OrderInfoDto struct {
	ID          string    `json:"id"`
	ShopName    string    `json:"shopName"`
	CusId       int64     `json:"cusId"`
	TableId     int64     `json:"tableId"`
	PayType     int       `json:"payType"`
	PayStatus   int       `json:"payStatus"`
	Commission  float64   `json:"commission"`
	PayAmount   float64   `json:"payAmount"`
	OrderStatus int       `json:"orderStatus"`
	Remark      string    `json:"remark"`
	ShopId      int64     `json:"shopId"`
	CreateTime  time.Time `json:"createTime"`
	UpdateTime  time.Time `json:"updateTime"`
	CreateBy    int64     `json:"createBy"`
	UpdateBy    int64     `json:"updateBy"`
	Deleted     int       `json:"deleted"`
}
