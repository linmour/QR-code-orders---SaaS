package service

import (
	"server/service/order/admin"
	"server/service/order/app"
	"server/service/system"
)

type Group struct {
	UserService     system.UserService
	OrderService    admin.OrderService
	AppOrderService app.AppOrderService
}

var GroupApp = new(Group)
