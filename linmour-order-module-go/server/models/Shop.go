package models

import "time"

type Shop struct {
	// 主键
	ID int64 `json:"id"` // JSON键名为驼峰形式

	// 店铺名称
	Name string `json:"name"`

	// 经营许可证等证书
	Certificate string `json:"certificate"`

	// 店铺简介
	Intro string `json:"intro"`

	// 店铺状态 0停用 1正常
	ShopStatus int `json:"shopStatus"` // JSON键名为驼峰形式

	// 审核状态 0未通过 1通过 2审核中
	AuditStatus int `json:"auditStatus"` // JSON键名为驼峰形式

	// 分店 0表示主店
	ParentID int64 `json:"parentId"` // JSON键名为驼峰形式

	// 店主
	MerchantID int64 `json:"merchantId"` // JSON键名为驼峰形式

	// 店铺位置
	Position string `json:"position"`

	// 经营状态 0休息中 1营业中
	BusinessStatus int `json:"businessStatus"` // JSON键名为驼峰形式

	// 营业时间
	BusinessHours time.Time `json:"businessHours"` // JSON键名为驼峰形式

	// 费率
	FeeRate float64 `json:"feeRate"` // JSON键名为驼峰形式

	// 创建时间
	CreateTime time.Time `json:"createTime"` // JSON键名为驼峰形式

	// 更新时间
	UpdateTime time.Time `json:"updateTime"` // JSON键名为驼峰形式

	// 是否已删除
	Deleted int `json:"deleted"`

	// 序列化版本号，Go中通常不需要
	// serialVersionUID int64 `json:"-"` // 这个字段在Go中通常不需要，所以注释掉
}
