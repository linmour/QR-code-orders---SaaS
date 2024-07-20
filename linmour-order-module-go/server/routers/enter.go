package routers

import (
	"server/routers/order"
	"server/routers/system"
)

type RouterGroup struct {
	System system.RouterGroup
	Order  order.BaseRouter
}

var RouterGroupApp = new(RouterGroup)
