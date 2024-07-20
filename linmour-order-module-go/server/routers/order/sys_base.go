package order

import (
	"github.com/gin-gonic/gin"
	"server/api"
)

type BaseRouter struct{}

func (s *BaseRouter) InitBaseRouter(Router *gin.RouterGroup) (R gin.IRoutes) {
	baseRouter := Router.Group("base")
	baseApi := api.GroupApp.OrderApiGroup.BaseApi
	{
		baseRouter.GET("GetOrderByShopId", baseApi.GetOrderByShopId)
		baseRouter.GET("GetCurrentOrderInfo/:tableId", baseApi.GetCurrentOrderInfo)
		baseRouter.GET("getOrderPayAmount", baseApi.GetOrderPayAmount)
		baseRouter.POST("changeOrderStatus", baseApi.ChangeOrderStatus)
		baseRouter.POST("createOrder", baseApi.CreateOrder)
		baseRouter.POST("checkout", baseApi.Checkout)
		baseRouter.GET("getHistoryOrderList/:tableId", baseApi.GetHistoryOrderList)
		baseRouter.GET("getOrderInfoDetail/:orderId", baseApi.GetOrderInfoDetail)
		baseRouter.POST("updateOrderItemStatus", baseApi.UpdateOrderItemStatus)
	}
	return baseRouter
}
