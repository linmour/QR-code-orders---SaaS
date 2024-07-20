package order

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"server/models"
)

func (b *BaseApi) GetOrderByShopId(c *gin.Context) {
	r, err := orderService.GetOrderByShopId()
	if err != nil {
		return
	}
	c.JSON(http.StatusOK, r)
}

func (b *BaseApi) GetCurrentOrderInfo(c *gin.Context) {
	r, err := orderService.GetCurrentOrderInfo(c.Param("tableId"))
	if err != nil {
		return
	}

	c.JSON(http.StatusOK, r)
}

func (b *BaseApi) GetOrderPayAmount(c *gin.Context) {
	var orderInfoPage models.OrderInfoPage
	if err := c.ShouldBindQuery(&orderInfoPage); err == nil {
		r, err := orderService.GetOrderPayAmount(orderInfoPage)
		if err != nil {
			return
		}

		c.JSON(http.StatusOK, r)
	} else {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
}

func (b *BaseApi) ChangeOrderStatus(c *gin.Context) {
	var params map[string]interface{}
	if err := c.ShouldBindJSON(&params); err == nil {
		// 成功绑定，params 包含了 JSON 请求体中的所有键值对
		c.JSON(http.StatusOK, params)
	} else {
		// 绑定失败，返回错误信息
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
	r, err := orderService.ChangeOrderStatus(params)
	if err != nil {
		return
	}
	c.JSON(http.StatusOK, r)
}

func (b *BaseApi) CreateOrder(c *gin.Context) {
	var createOrderDto models.CreateOrderDto
	if err := c.ShouldBindQuery(&createOrderDto); err == nil {
		r := appOrderService.CreateOrder(&createOrderDto)
		c.JSON(http.StatusOK, r)
	} else {

		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
}

func (b *BaseApi) Checkout(c *gin.Context) {
	var checkoutDto models.CheckoutDto
	if err := c.ShouldBindQuery(&checkoutDto); err == nil {
		r := appOrderService.Checkout(&checkoutDto)
		c.JSON(http.StatusOK, r)
	} else {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
}

func (b *BaseApi) GetHistoryOrderList(c *gin.Context) {
	r, err := appOrderService.GetHistoryOrderList(c.Param("tableId"))
	if err != nil {
		return
	}

	c.JSON(http.StatusOK, r)
}
func (b *BaseApi) GetOrderInfoDetail(c *gin.Context) {
	r, err := appOrderService.GetHistoryOrderList(c.Param("orderId"))
	if err != nil {
		return
	}

	c.JSON(http.StatusOK, r)
}

func (b *BaseApi) UpdateOrderItemStatus(c *gin.Context) {
	var params map[string]interface{}
	if err := c.ShouldBindJSON(&params); err == nil {
		// 成功绑定，params 包含了 JSON 请求体中的所有键值对
		c.JSON(http.StatusOK, params)
	} else {
		// 绑定失败，返回错误信息
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
	}
	r, err := orderService.ChangeOrderStatus(params)
	if err != nil {
		return
	}
	c.JSON(http.StatusOK, r)
}
