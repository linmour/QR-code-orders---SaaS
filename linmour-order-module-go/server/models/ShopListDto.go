package models

type ShopListDto struct {
	ID      int64   `json:"id"`
	Name    string  `json:"name"`
	ShopId  int64   `json:"shopId"`
	Intro   string  `json:"intro"`
	Price   float64 `json:"price"`
	SpecId  int64   `json:"specId"`
	Status  bool    `json:"status"`
	Picture string  `json:"picture"`
	SortId  int64   `json:"sortId"`
	Number  int     `json:"number"`
}
