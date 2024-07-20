package grpcclient

import (
	"context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	pb "server/proto"
)

type ProductClient struct {
	client pb.ProductClient
}

// ConnectServer 封装连接到 gRPC 服务器的逻辑
func ConnectProductServer(address string) (*ProductClient, error) {
	conn, err := grpc.Dial(address, grpc.WithTransportCredentials(insecure.NewCredentials()), grpc.WithBlock())
	if err != nil {
		return nil, err
	}
	client := pb.NewProductClient(conn)
	return &ProductClient{client: client}, nil
}

// GetProductDetails 封装调用服务端 GetProductDetails 方法的逻辑
func (c *ProductClient) GetProductDetails(longList *pb.LongList) (*pb.Result, error) {
	return c.client.GetProductDetails(context.Background(), longList)
}
func (c *ProductClient) ReduceInventories(longList *pb.LongList) (*pb.Result1, error) {
	return c.client.ReduceInventories(context.Background(), longList)
}
