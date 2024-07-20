package api

import (
	"server/api/order"
	"server/api/system"
)

type Group struct {
	SystemApiGroup system.ApiGroup
	OrderApiGroup  order.ApiGroup
}

var GroupApp = new(Group)
