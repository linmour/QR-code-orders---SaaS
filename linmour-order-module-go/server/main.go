package main

import (
	"fmt"
	"github.com/segmentio/kafka-go"
	"go.uber.org/zap"
	_ "google.golang.org/grpc"
	_ "google.golang.org/grpc/reflection"
	"io/ioutil"
	"log"
	"net/http"
	"server/core"
	"server/global"
	"server/grpc/grpcclient"
	"server/grpc/grpcserver"
	"server/initialize"
)

func main() {
	// 读取配置文件
	core.InitConf()
	// 连接数据库
	global.DB = core.InitGorm()
	//router := routers.Router()
	//log.Printf("程序运行在%s\n", global.Config.System.Addr())
	//err := router.Run(global.Config.System.Addr())
	//if err != nil {
	//	return
	//}
	//读取配置信息
	global.GVA_VP = core.Viper()
	// 初始化其他配置
	initialize.OtherInit()
	// 加载zap日志配置
	global.GVA_LOG = core.Zap()
	zap.ReplaceGlobals(global.GVA_LOG)
	// 连接数据库
	global.GVA_DB = initialize.GormMysql()
	//initialize.Timer()

	// 启动gRPC服务器
	server := grpcserver.Server{}
	go grpcserver.RunServer(":29898", &server)

	// 创建封装的 gRPC 客户端
	global.ProductClient, _ = grpcclient.ConnectProductServer(":19898")

	global.SystemClient, _ = grpcclient.ConnectSystemServer(":20001")
	//longList := &pb.LongList{Values: []int64{1, 2, 3}}
	//global.SystemClient.GetMerchantByIds(longList)
	//if err != nil {
	//	log.Fatalf("failed to connect: %v", err)
	//}
	//defer client.Close()
	//// 创建 LongList 请求
	//longList := &pb.LongList{Values: []int64{1, 2, 3}}
	//// 调用封装的 GetProductDetails 方法
	//productDetails, err := global.ProductClient.GetProductDetails(longList)
	//if err != nil {
	//	log.Fatalf("could not get product details: %v", err)
	//}
	//// 处理响应
	//fmt.Printf("Received product details: %v\n", productDetails)
	//if err != nil {
	//	log.Fatalf("could not greet: %v", err)
	//}

	//client, err := NewClient(address) //config := sarama.NewConfig()
	//config.Producer.RequiredAcks = sarama.WaitForAll
	//config.Producer.Retry.Max = 5
	//config.Producer.Return.Successes = true
	//
	//// 创建 Kafka 生产者
	//producer, err := sarama.NewSyncProducer([]string{"120.79.7.243:9092"}, config)
	//if err != nil {
	//	log.Fatalf("Creating producer: %v", err)
	//}
	//// 延迟关闭生产者链接
	//defer producer.Close()
	//
	//// 定义消息 Topic是 go-test, 值为 Hello Kafka
	//message := &sarama.ProducerMessage{
	//	Topic: "go-test",
	//	Value: sarama.StringEncoder("Hello Kafka!"),
	//}
	//
	//// 发送消息
	//for i := 0; i < 10; i++ {
	//	partition, offset, err := producer.SendMessage(message)
	//	if err != nil {
	//		log.Fatalf("Sending message: %v", err)
	//	}
	//	log.Printf("Message sent to partition %d at offset %d", partition, offset)
	//	time.Sleep(time.Second)
	//}
	//
	//// 配置 Kafka 消费者
	//config.Consumer.Return.Errors = true
	//
	//// 创建 Kafka 消费者
	//consumer, err := sarama.NewConsumer([]string{"120.79.7.243:9092"}, config)
	//if err != nil {
	//	log.Fatal(err)
	//}
	//// 延迟关闭消费者链接
	//defer consumer.Close()
	////订阅主题，获取分区 partition
	//partitionConsumer, err := consumer.ConsumePartition("go-test", 0, sarama.OffsetOldest)
	//if err != nil {
	//	log.Fatalf("Consuming partition: %v", err)
	//}
	//// 延迟关闭分区链接
	//defer partitionConsumer.Close()
	//
	//// 消费消息
	//for {
	//	select {
	//	// 从 分区 通道中获取信息
	//	case msg := <-partitionConsumer.Messages():
	//		log.Printf("Received message: %s", string(msg.Value))
	//	// 如果从通道中获取消息失败
	//	case err := <-partitionConsumer.Errors():
	//		log.Fatalf("Received error: %v", err)
	//	}
	//}

	core.RunWindowsServer()
}

func producerHandler(kafkaWriter *kafka.Writer) func(http.ResponseWriter, *http.Request) {
	return http.HandlerFunc(func(wrt http.ResponseWriter, req *http.Request) {
		body, err := ioutil.ReadAll(req.Body)
		if err != nil {
			log.Fatalln(err)
		}
		msg := kafka.Message{
			Key:   []byte(fmt.Sprintf("address-%s", req.RemoteAddr)),
			Value: body,
		}
		err = kafkaWriter.WriteMessages(req.Context(), msg)

		if err != nil {
			wrt.Write([]byte(err.Error()))
			log.Fatalln(err)
		}
	})
}
