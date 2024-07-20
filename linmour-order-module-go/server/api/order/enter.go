package order

import "server/service"

type ApiGroup struct {
	BaseApi BaseApi
}

var (
	orderService    = service.GroupApp.OrderService
	appOrderService = service.GroupApp.AppOrderService
)
