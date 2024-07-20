package core

import (
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"log"
	"server/global"
	"time"
)

func InitGorm() *gorm.DB {
	if global.GVA_CONFIG.Mysql.Path == "" {
		//global.Log.Warn
		log.Println("未配置mysql,取消Mysql连接")
		return nil
	}

	dsn := global.GVA_CONFIG.Mysql.Dsn()

	var mysqlLogger logger.Interface
	if global.GVA_CONFIG.System.Env == "debug" {
		mysqlLogger = logger.Default.LogMode(logger.Info)
	} else {
		mysqlLogger = logger.Default.LogMode(logger.Error)
	}

	db, err := gorm.Open(mysql.Open("root:cheng128@tcp(120.79.7.243:3306)/lsc_order?charset=utf8mb4&parseTime=True&loc=Local"), &gorm.Config{
		Logger: mysqlLogger,
	})
	if err != nil {
		//global.Log()
		log.Fatalf("[%s]mysql连接失败", dsn)
		//panic(err)
	}
	sqlDb, _ := db.DB()
	sqlDb.SetConnMaxLifetime(time.Hour * 4)
	sqlDb.SetMaxIdleConns(10)  // 最大空闲链接
	sqlDb.SetMaxOpenConns(100) // 最多可容纳
	return db
}
