package config

type Config struct {
	Mysql  Mysql  `yaml:"mysql"`
	Logger Logger `yaml:"logger"`
}

type Server struct {
	System   System   `yaml:"system"`
	Mongo    Mongo    `mapstructure:"mongo" json:"mongo" yaml:"mongo"`
	JWT      JWT      `mapstructure:"jwt" json:"jwt" yaml:"jwt"`
	Mysql    Mysql    `mapstructure:"mysql" json:"mysql" yaml:"mysql"`
	AutoCode Autocode `mapstructure:"autocode" json:"autocode" yaml:"autocode"`
	Zap      Zap      `mapstructure:"zap" json:"zap" yaml:"zap"`
	Redis    Redis    `mapstructure:"redis" json:"redis" yaml:"redis"`
	Local    Local    `mapstructure:"local" json:"local" yaml:"local"`
}
