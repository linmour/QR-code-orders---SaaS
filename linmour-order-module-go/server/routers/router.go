package routers

import "github.com/gin-gonic/gin"

func Router() *gin.Engine {
	router := gin.New()

	router.GET("", func(c *gin.Context) {
		c.String(200, "xxx")
	})
	return router
}
