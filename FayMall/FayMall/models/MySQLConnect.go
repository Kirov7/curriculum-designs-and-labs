package models

import (
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
)

var DB *gorm.DB
var err error

func init() {
	mysqladmin, _ := beego.AppConfig.String("mysqladmin")
	mysqlpwd, _ := beego.AppConfig.String("mysqlpwd")
	mysqldb, _ := beego.AppConfig.String("mysqldb")
	DB, err =
		gorm.Open("mysql", mysqladmin+":"+mysqlpwd+"@/"+mysqldb+"?charset=utf8"+
			"&parseTime=True&loc=Local")
	if err != nil {
		logs.Error(err)
		logs.Error("连接MySql数据库失败")
	} else {
		logs.Error("连接MySql数据库成功")
	}
}
