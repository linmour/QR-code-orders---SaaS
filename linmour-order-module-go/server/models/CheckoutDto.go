package models

type CheckoutDto struct {
	TableId string `json:"tableId"`
	PayType int    `json:"payType"`
	Openid  string `json:"openid"`
}
