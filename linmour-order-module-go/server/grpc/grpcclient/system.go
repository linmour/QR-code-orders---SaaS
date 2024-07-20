package grpcclient

import (
	"context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
	pb "server/proto"
)

type SystemClient struct {
	client pb.SystemClient
}

func ConnectSystemServer(address string) (*SystemClient, error) {
	conn, err := grpc.Dial(address, grpc.WithTransportCredentials(insecure.NewCredentials()), grpc.WithBlock())
	if err != nil {
		return nil, err
	}
	client := pb.NewSystemClient(conn)
	return &SystemClient{client: client}, nil
}

func (c *SystemClient) GetShopByIds(longList *pb.LongList) (*pb.Result, error) {
	return c.client.GetShopByIds(context.Background(), longList)
}
func (c *SystemClient) GetMerchantByIds(longList *pb.LongList) (*pb.Result, error) {
	return c.client.GetMerchantByIds(context.Background(), longList)
}
