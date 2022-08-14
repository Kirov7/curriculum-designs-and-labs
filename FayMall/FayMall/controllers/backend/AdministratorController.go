package backend

import (
	"FayMall/common"
	"FayMall/models"
	"strconv"
	"strings"
)

type AdministratorController struct {
	BaseController
}

func (c *AdministratorController) Get() {
	administrator := []models.Administrator{}
	models.DB.Preload("Role").Find(&administrator)
	c.Data["administratorList"] = administrator
	c.TplName = "backend/administrator/index.html"
}

func (c *AdministratorController) Add() {
	role := []models.Role{}
	models.DB.Find(&role)
	c.Data["roleList"] = role
	c.TplName = "backend/administrator/add.html"
}

func (c *AdministratorController) GoAdd() {
	username := strings.Trim(c.GetString("username"), "")
	password := strings.Trim(c.GetString("password"), "")
	mobile := strings.Trim(c.GetString("mobile"), "")
	email := strings.Trim(c.GetString("email"), "")
	roleId, err1 := c.GetInt("role_id")
	if err1 != nil {
		c.Error("非法请求", "/administrator/add")
	}
	if len(username) < 2 || len(password) < 6 {
		c.Error("用户名或密码长度不合法", "/administrator/add")
		return
	} else if common.VerifyEmail(email) == false {
		c.Error("邮箱格式不正确，请重新填写!", "/administrator/add")
		return
	}
	administratorList := []models.Administrator{}
	models.DB.Where("username=?", username).Find(&administratorList)
	if len(administratorList) > 0 {
		c.Error("用户名已存在", "/administrator/add")
		return
	}

	administrator := models.Administrator{}
	administrator.Username = username
	administrator.Password = common.Md5(password)
	administrator.Mobile = mobile
	administrator.Email = email
	administrator.Status = 1
	administrator.AddTime = int(common.GetUnix())
	administrator.RoleId = roleId
	err := models.DB.Create(&administrator).Error
	if err != nil {
		c.Error("增加管理员失败", "/administrator/add")
		return
	}
	c.Success("增加管理员成功", "/administrator")
}

func (c *AdministratorController) Edit() {
	id, err := c.GetInt("id")
	if err != nil {
		c.Error("传入参数错误", "/administrator")
		return
	}
	administrator := models.Administrator{Id: id}
	models.DB.Find(&administrator)
	c.Data["administrator"] = administrator
	role := []models.Role{}
	models.DB.Find(&role)
	c.Data["roleList"] = role
	c.TplName = "backend/administrator/edit.html"
}

func (c *AdministratorController) GoEdit() {
	id, err := c.GetInt("id")
	if err != nil {
		c.Error("传入参数错误", "/administrator")
		return
	}
	username := strings.Trim(c.GetString("Username"), "")
	password := strings.Trim(c.GetString("Password"), "")
	mobile := strings.Trim(c.GetString("Mobile"), "")
	email := strings.Trim(c.GetString("Email"), "")
	roleId, err1 := c.GetInt("role_id")
	if err1 != nil {
		c.Error("非法请求", "/administrator")
		return
	}
	if password != "" {
		if len(password) < 6 {
			c.Error("密码长度不合法！", "/administrator/add?id="+strconv.Itoa(id))
			return
		} else if common.VerifyEmail(email) == false {
			c.Error("邮箱格式不正确，请重新填写!", "/administrator/add?id="+strconv.Itoa(id))
			return
		}
		password = common.Md5(password)
	}
	administrator := models.Administrator{Id: id}
	models.DB.Find(&administrator)
	administrator.Username = username
	administrator.Password = password
	administrator.Mobile = mobile
	administrator.Email = email
	administrator.RoleId = roleId
	err2 := models.DB.Save(&administrator).Error
	if err2 != nil {
		c.Error("修改管理员失败", "/administrator/edit?id="+strconv.Itoa(id))
	} else {
		c.Success("修改管理员成功", "/administrator")
	}
}

func (c *AdministratorController) Delete() {
	id, err := c.GetInt("id")
	if err != nil {
		c.Error("传入参数错误", "/role")
		return
	}
	administrator := models.Administrator{Id: id}
	models.DB.Delete(&administrator)
	c.Success("删除管理员成功", "/administrator")
}
