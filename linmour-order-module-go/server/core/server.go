package core

import (
	"fmt"
	"go.uber.org/zap"
	"server/global"
	"server/initialize"
)

type server interface {
	ListenAndServe() error
}

func RunWindowsServer() {
	if global.GVA_CONFIG.System.UseMultipoint || global.GVA_CONFIG.System.UseRedis {
		// 初始化redis服务
		initialize.InitRedis()
	}
	// 初始化mongo
	if global.GVA_CONFIG.System.UseMongo {
		err := initialize.Mongo.Mongo()
		if err != nil {
			zap.L().Error(fmt.Sprintf("%+v", err))
		}
	}

	// 初始化路由
	Router := initialize.Routers()
	Router.Static("/form-generator", "./resource/page")
	// 监听8080端口
	address := fmt.Sprintf(":%s", global.GVA_CONFIG.System.Port)
	s := initServer(address, Router)
	// 打印错误日志
	global.GVA_LOG.Error(s.ListenAndServe().Error())
}
