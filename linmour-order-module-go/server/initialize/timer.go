package initialize

//func Timer() {
//	go func() {
//		var option []cron.Option
//		option = append(option, cron.WithSeconds())
//		// 清理DB定时任务
//		_, err := global.GVA_Timer.AddTaskByFunc("ClearDB", "@daily", func() {
//			err := task.ClearTable(global.GVA_DB) // 定时任务方法定在task文件包中
//			if err != nil {
//				fmt.Println("timer error:", err)
//			}
//		}, "定时清理数据库【日志，黑名单】内容", option...)
//		if err != nil {
//			fmt.Println("add timer error:", err)
//		}
//	}()
//}
