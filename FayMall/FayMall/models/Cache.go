package models

import (
	"context"
	"encoding/json"
	"fmt"
	"github.com/beego/beego/v2/core/logs"

	"github.com/beego/beego/v2/client/cache"
	_ "github.com/beego/beego/v2/client/cache/redis"
	beego "github.com/beego/beego/v2/server/web"

	"time"
)

var redisClient cache.Cache
var enableRedis, _ = beego.AppConfig.Bool("enableRedis")
var redisTime, _ = beego.AppConfig.Int("redisTime")
var YzmClient cache.Cache

func init() {
	if enableRedis {
		key, _ := beego.AppConfig.String("redisKey")
		conn, _ := beego.AppConfig.String("redisConn")
		dbNum, _ := beego.AppConfig.String("redisDbNum")
		password, _ := beego.AppConfig.String("redisPwd")

		config := map[string]string{
			"key":      key,
			"conn":     conn,
			"dbNum":    dbNum,
			"password": password,
		}
		bytes, _ := json.Marshal(config)

		redisClient, err = cache.NewCache("redis", string(bytes))
		YzmClient, _ = cache.NewCache("redis", string(bytes))
		if err != nil {
			logs.Error("连接redis数据库失败", err)
		} else {
			logs.Info("连接redis数据库成功")
		}

	}
}

type cacheDb struct{}

var CacheDb = &cacheDb{}

//写入数据的方法
func (c cacheDb) Set(key string, value interface{}) {
	if enableRedis {
		bytes, _ := json.Marshal(value)
		redisClient.Put(context.TODO(), key, string(bytes), time.Second*time.Duration(redisTime))
	}
}

//获取数据的方法
func (c cacheDb) Get(key string, obj interface{}) bool {
	if enableRedis {
		if redisStr, _ := redisClient.Get(context.TODO(), key); redisStr != nil {
			fmt.Println("在redis里面读取数据...")
			redisValue, ok := redisStr.([]uint8)
			if !ok {
				fmt.Println("获取redis数据失败")
				return false
			}
			json.Unmarshal([]byte(redisValue), obj)
			return true
		}
		return false
	}
	return false
}
