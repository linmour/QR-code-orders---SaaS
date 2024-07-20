package global

import (
	"github.com/qiniu/qmgo"
	"github.com/redis/go-redis/v9"
	"github.com/songzhibin97/gkit/cache/local_cache"
	"github.com/spf13/viper"
	"go.uber.org/zap"
	"gorm.io/gorm"
	"server/config"
	"server/grpc/grpcclient"
	"server/utils/timer"
)

var (
	GVA_MONGO     *qmgo.QmgoClient
	RedisClient   redis.UniversalClient
	Config        *config.Config
	DB            *gorm.DB
	GVA_CONFIG    *config.Server
	GVA_VP        *viper.Viper
	BlackCache    local_cache.Cache
	GVA_LOG       *zap.Logger
	GVA_DB        *gorm.DB
	GVA_Timer     timer.Timer = timer.NewTimerTask()
	ProductClient *grpcclient.ProductClient
	SystemClient  *grpcclient.SystemClient
)

const (
	ConfigEnv         = "GVA_CONFIG"
	ConfigDefaultFile = "config.yaml"
	ConfigTestFile    = "config.test.yaml"
	ConfigDebugFile   = "config.debug.yaml"
	ConfigReleaseFile = "config.release.yaml"
)
