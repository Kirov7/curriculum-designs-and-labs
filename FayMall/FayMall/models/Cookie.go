package models

import (
	"encoding/json"

	beego "github.com/beego/beego/v2/server/web"
	"github.com/beego/beego/v2/server/web/context"
)

//定义结构体  缓存结构体 私有
type cookie struct{}

// Set 写入数据的方法
func (c cookie) Set(ctx *context.Context, key string, value interface{}) {
	bytes, _ := json.Marshal(value)
	cookie, _ := beego.AppConfig.String("secureCookie")
	domain, _ := beego.AppConfig.String("domain")
	ctx.SetSecureCookie(cookie, key, string(bytes), 3600*24*30, "/", domain, nil, true)

}

// Remove 删除数据的方法
func (c cookie) Remove(ctx *context.Context, key string, value interface{}) {
	bytes, _ := json.Marshal(value)
	cookie, _ := beego.AppConfig.String("secureCookie")
	domain, _ := beego.AppConfig.String("domain")
	ctx.SetSecureCookie(cookie, key, string(bytes), -1, "/", domain, nil, true)

}

// Get 获取数据的方法
func (c cookie) Get(ctx *context.Context, key string, obj interface{}) bool {
	cookie, _ := beego.AppConfig.String("secureCookie")
	tempData, ok := ctx.GetSecureCookie(cookie, key)
	if !ok {
		return false
	}
	json.Unmarshal([]byte(tempData), obj)
	return true

}

// Cookie 实例化结构体
var Cookie = &cookie{}
