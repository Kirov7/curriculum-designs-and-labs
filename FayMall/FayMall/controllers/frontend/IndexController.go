package frontend

import (
	"FayMall/models"
	"fmt"
	"time"
)

// IndexController 继承自BaseController
type IndexController struct {
	BaseController
}

func (c *IndexController) Get() {

	//调用功能功能
	c.BaseInit()

	//开始时间
	startTime := time.Now().UnixNano()

	//获取轮播图 注意获取的时候要写地址
	banner := []models.Banner{}
	if hasBanner := models.CacheDb.Get("banner", &banner); hasBanner == true {
		c.Data["bannerList"] = banner
	} else {
		models.DB.Where("status=1 AND banner_type=1").Order("sort desc").Find(&banner)
		c.Data["bannerList"] = banner
		models.CacheDb.Set("banner", banner)
	}

	//获取手机商品列表
	redisPhone := []models.Product{}
	if hasPhone := models.CacheDb.Get("phone", &redisPhone); hasPhone == true {
		c.Data["phoneList"] = redisPhone
	} else {
		phone := models.GetProductByCategory(1, "hot", 8)
		c.Data["phoneList"] = phone
		models.CacheDb.Set("phone", phone)
	}
	//获取电视商品列表
	redisTv := []models.Product{}
	if hasTv := models.CacheDb.Get("tvList", &redisTv); hasTv == true {
		c.Data["tvList"] = redisTv
	} else {
		tv := models.GetProductByCategory(2, "best", 8)
		c.Data["tvList"] = tv
		models.CacheDb.Set("tv", tv)
	}

	//结束时间
	endTime := time.Now().UnixNano()

	fmt.Println("执行时间", endTime-startTime)

	c.TplName = "frontend/index/index.html"
}
