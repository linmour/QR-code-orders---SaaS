package system

import "server/service"

type ApiGroup struct {
	BaseApi BaseApi
}

var (
	userService = service.GroupApp.UserService
)
