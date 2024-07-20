package grpcserver

import (
	"context"
	"encoding/json"
	"fmt"
	"google.golang.org/grpc"
	"log"
	"net"
	"server/models"
	pb "server/proto"
	"server/service"
)

// Server 结构体，实现了pb.OrderServer接口
type Server struct {
	pb.UnimplementedOrderServer
}

func (s *Server) CreateOrder(ctx context.Context, in *pb.Object) (*pb.Result, error) {
	var a models.CreateOrderDto
	json.Unmarshal([]byte(in.GetData()), &a)
	service.GroupApp.AppOrderService.CreateOrder(&a)
	fmt.Printf("%+v\n", a)
	return &pb.Result{Code: 200, Msg: "Order created successfully"}, nil
}

// RunServer 启动gRPC服务器
func RunServer(address string, serverImpl *Server) {
	lis, err := net.Listen("tcp", address)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterOrderServer(s, serverImpl)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
