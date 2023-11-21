package config

import (
	"encoding/json"
	"flag"
	"github.com/redis/go-redis/v9"
	log "github.com/sirupsen/logrus"
	"github.com/spf13/viper"
	"gopkg.in/natefinch/lumberjack.v2"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"
	"path/filepath"
	"strings"
	"sync"
	"time"
)

var once sync.Once

type Config struct {
	Server string
	Log    struct {
		Filename string
		// M
		MaxSize int
		// Day
		MaxAge          int
		Level           string
		ReportCaller    bool
		OutputToConsole bool
	}
	Redis struct {
		Addr       string
		Password   string
		DB         int
		MaxRetries int
		// ms
		DialTimeout time.Duration
		// ms
		ReadTimeout time.Duration
		// ms
		WriteTimeout time.Duration
		PoolSize     int
		MinIdleConns int
		// S
		ConnMaxIdleTime time.Duration
	}
	DB struct {
		Dsn          string
		MaxIdleConns int
		// S
		ConnMaxLifetime time.Duration
		// S
		ConnMaxIdleTime time.Duration
		MaxOpenConns    int
	}
}

var (
	ServerConfigPath = defaultConfigPath
	Global           = new(Config)
	RedisClient      *redis.Client
	SqlClient        *gorm.DB
)

const (
	defaultConfigPath = "./conf"
	configName        = "application"
	configType        = "yaml"
)

// Init 初始化业务全局配置
// config file dir default is ./conf and can be set by flag -configPath
func Init() {
	once.Do(func() {
		// 执行文件 -h 可以查看说明
		if ServerConfigPath == defaultConfigPath {
			flag.StringVar(&ServerConfigPath, "configPath", defaultConfigPath, "server config path")
			if !flag.Parsed() {
				flag.Parse()
			}
		}
		fullConfigPath, err := filepath.Abs(ServerConfigPath)
		if err != nil {
			log.Fatalf("find configPath abs err: %s", err)
		}
		log.Infof("read config file from %s", fullConfigPath)
		viper := viper.New()
		viper.AddConfigPath(fullConfigPath)
		viper.SetConfigName(configName)
		viper.SetConfigType(configType)
		// 环境变量不能有点，viper 对大小写的都能识别
		viper.SetEnvKeyReplacer(strings.NewReplacer(".", "_"))
		viper.AutomaticEnv()
		if err = viper.ReadInConfig(); err != nil {
			log.Fatalf("read config file error:%s", err)
		}
		if err = viper.Unmarshal(Global); err != nil {
			log.Fatalf("unmarshal config file error:%s", err)
		}
		if marshal, err := json.Marshal(Global); err != nil {
			log.Fatalf("json unmarshal error:%s", err)
		} else {
			log.Printf("config is:%s\n", marshal)
		}
		initLog()
		initRedis()
		initDB()
	})
}

// 初始化log https://github.com/sirupsen/logrus
func initLog() {
	logConf := Global.Log
	level, err := log.ParseLevel(logConf.Level)
	if err != nil {
		panic(err)
	}
	log.SetLevel(level)

	logger := &lumberjack.Logger{
		Filename:   logConf.Filename,
		MaxSize:    logConf.MaxSize,
		MaxAge:     logConf.MaxAge,
		MaxBackups: 0,
		LocalTime:  true,
	}
	log.SetReportCaller(logConf.ReportCaller)
	if !logConf.OutputToConsole {
		log.SetOutput(logger)
	}
}

// 初始化redis https://github.com/redis/go-redis
func initRedis() {
	redisConf := Global.Redis
	RedisClient = redis.NewClient(&redis.Options{
		Addr:            redisConf.Addr,
		Password:        redisConf.Password,
		DB:              redisConf.DB,
		MaxRetries:      redisConf.MaxRetries,
		DialTimeout:     redisConf.DialTimeout * time.Millisecond,
		ReadTimeout:     redisConf.ReadTimeout * time.Millisecond,
		WriteTimeout:    redisConf.WriteTimeout * time.Millisecond,
		PoolSize:        redisConf.PoolSize,
		MinIdleConns:    redisConf.MinIdleConns,
		ConnMaxIdleTime: redisConf.ConnMaxIdleTime * time.Second,
	})
}

// https://github.com/go-sql-driver/mysql
// https://gorm.io/zh_CN/docs/connecting_to_the_database.html
func initDB() {
	dbConf := Global.DB
	// timeout is for connection timeout
	dsn := dbConf.Dsn
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{
		NamingStrategy: schema.NamingStrategy{
			// 驼峰表名，不加s
			SingularTable: true,
		},
	})
	if err != nil {
		log.Fatalf("connect db error:%s", err)
	}
	// 添加连接池
	sqlDB, err := db.DB()
	if err != nil {
		log.Fatalf("connect db error:%s", err)
	}
	sqlDB.SetMaxIdleConns(dbConf.MaxIdleConns)
	sqlDB.SetConnMaxLifetime(dbConf.ConnMaxLifetime * time.Second)
	sqlDB.SetConnMaxIdleTime(dbConf.ConnMaxIdleTime * time.Second)
	sqlDB.SetMaxOpenConns(dbConf.MaxOpenConns)
	SqlClient = db
}
