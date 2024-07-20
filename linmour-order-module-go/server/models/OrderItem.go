package models

// OrderItem 代表订单条目信息的结构体
type OrderItem struct {
	ID         string  `json:"id" gorm:"column:id"`                  // 订单条目ID
	Name       string  `json:"name" gorm:"column:name"`              // 商品名字
	ShopId     string  `json:"shopId" gorm:"column:shop_id"`         // 商店ID
	Status     int16   `json:"status" gorm:"column:status"`          // 状态
	ProductID  int64   `json:"productID" gorm:"column:product_id"`   // 商品ID
	SortID     string  `json:"sortID" gorm:"column:sort_id"`         // 分类ID
	Picture    string  `json:"picture" gorm:"column:picture"`        // 商品图
	Price      float64 `json:"price" gorm:"column:price"`            // 价格
	Sort       string  `json:"sort" gorm:"column:sort"`              // 分类
	PropsText  string  `json:"propsText" gorm:"column:props_text"`   // 规格文本
	Quantity   int16   `json:"quantity" gorm:"column:quantity"`      // 数量
	OrderID    string  `json:"orderID" gorm:"column:order_id"`       // 订单ID
	CreateTime string  `json:"createTime" gorm:"column:create_time"` // 创建时间
	UpdateTime string  `json:"updateTime" gorm:"column:update_time"` // 更新时间
	CreateBy   string  `json:"createBy" gorm:"column:create_by"`     // 创建人
	UpdateBy   string  `json:"updateBy" gorm:"column:update_by"`     // 更新人
	Deleted    int     `json:"deleted" gorm:"column:deleted"`        // 是否删除
}
