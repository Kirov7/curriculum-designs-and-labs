package api

import (
	"FayMall/models"
	beego "github.com/beego/beego/v2/server/web"
)

type V1Controller struct {
	beego.Controller
}

func (c *V1Controller) Get() {
	c.Ctx.WriteString("api v1")
}

func (c *V1Controller) Menu() {
	menu := []models.Menu{}
	models.DB.Find(&menu)
	c.Data["json"] = menu
	c.ServeJSON()
}
