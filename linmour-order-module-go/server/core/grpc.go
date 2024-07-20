package core

import (
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"log"
	"net"
	pb "server/proto"
)

func InitGrpc() *grpc.Server {
	// 设置服务端监听地址
	lis, err := net.Listen("tcp", ":29898")
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	// 创建 gRPC 服务
	s := grpc.NewServer()
	pb.RegisterOrderServer(s, &grpcServer{})

	reflection.Register(s)
	go func() {
		// 在新的 Goroutine 中启动服务端
		if err := s.Serve(lis); err != nil {
			log.Fatalf("failed to serve: %v", err)
		}
	}()

	return s
}

type grpcServer struct {
	pb.UnimplementedOrderServer
}
