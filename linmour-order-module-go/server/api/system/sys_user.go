package system

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func (b *BaseApi) Login(c *gin.Context) {
	// 待实现 TODO...
	_, err := userService.Login()
	if err != nil {
		return
	}
	c.JSON(http.StatusOK, gin.H{"msg": "登录成功!"})
}
