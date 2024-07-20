package initialize

import (
	"context"
	"fmt"
	"github.com/pkg/errors"
	"github.com/qiniu/qmgo"
	"github.com/qiniu/qmgo/options"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/event"
	opt "go.mongodb.org/mongo-driver/mongo/options"
	"go.uber.org/zap"
	"server/global"
)

type (
	mongo struct{}
	Index struct {
		V    any      `bson:"v"`
		Ns   any      `bson:"ns"`
		Key  []bson.E `bson:"key"`
		Name string   `bson:"name"`
	}
)

var Mongo = new(mongo)

func (m *mongo) Mongo() error {
	var opts []options.ClientOptions
	if global.GVA_CONFIG.Mongo.IsZap {
		opts = GetClientOptions()
	}

	ctx := context.Background()
	client, err := qmgo.Open(ctx, &qmgo.Config{
		Uri:              global.GVA_CONFIG.Mongo.Uri(),
		Coll:             global.GVA_CONFIG.Mongo.Coll,
		Database:         global.GVA_CONFIG.Mongo.Database,
		MinPoolSize:      &global.GVA_CONFIG.Mongo.MinPoolSize,
		MaxPoolSize:      &global.GVA_CONFIG.Mongo.MaxPoolSize,
		SocketTimeoutMS:  &global.GVA_CONFIG.Mongo.SocketTimeoutMs,
		ConnectTimeoutMS: &global.GVA_CONFIG.Mongo.ConnectTimeoutMs,
		Auth: &qmgo.Credential{
			Username:   global.GVA_CONFIG.Mongo.Username,
			Password:   global.GVA_CONFIG.Mongo.Password,
			AuthSource: global.GVA_CONFIG.Mongo.AuthSource,
		},
	}, opts...)
	if err != nil {
		return errors.Wrap(err, "链接mongodb数据库失败!")
	}
	global.GVA_MONGO = client
	zap.L().Info(fmt.Sprintf("连接mongo成功"))
	// 初始化索引列表
	err = m.Indexes(ctx)
	if err != nil {
		return err
	}
	return nil
}

func (m *mongo) Indexes(ctx context.Context) error {
	// 表名:索引列表 列: "表名": [][]string{{"index1", "index2"}}
	indexMap := map[string][][]string{}

	for _, _ = range indexMap {
		// 待实现 TODO...
	}
	return nil
}

func GetClientOptions() []options.ClientOptions {
	cmdMonitor := &event.CommandMonitor{
		Started: func(ctx context.Context, event *event.CommandStartedEvent) {
			zap.L().Info(fmt.Sprintf("[MongoDB][RequestID:%d][database:%s] %s\n", event.RequestID, event.DatabaseName, event.Command), zap.String("business", "mongo"))
		},
		Succeeded: func(ctx context.Context, event *event.CommandSucceededEvent) {
			zap.L().Info(fmt.Sprintf("[MongoDB][RequestID:%d] [%s] %s\n", event.RequestID, event.Duration.String(), event.Reply), zap.String("business", "mongo"))
		},
		Failed: func(ctx context.Context, event *event.CommandFailedEvent) {
			zap.L().Error(fmt.Sprintf("[MongoDB][RequestID:%d] [%s] %s\n", event.RequestID, event.Duration.String(), event.Failure), zap.String("business", "mongo"))
		},
	}
	return []options.ClientOptions{{ClientOptions: &opt.ClientOptions{Monitor: cmdMonitor}}}
}
