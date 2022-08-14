package common

import (
	"FayMall/models"

	beego "github.com/beego/beego/v2/server/web"
	"github.com/beego/beego/v2/server/web/context"
	"net/url"
	"strings"
)

//后台权限判断
func BackendAuth(ctx *context.Context) {
	pathname := ctx.Request.URL.String()
	userinfo, ok := ctx.Input.Session("userinfo").(models.Administrator)
	adminPath, _ := beego.AppConfig.String("adminPath")
	if !(ok && userinfo.Username != "") {
		if pathname != "/"+adminPath+"/login" &&
			pathname != "/"+adminPath+"/login/gologin" &&
			pathname != "/"+adminPath+"/login/verificode" {
			ctx.Redirect(302, "/"+adminPath+"/login")
		}
	} else {
		pathname = strings.Replace(pathname, "/"+adminPath, "", 1)
		urlPath, _ := url.Parse(pathname)
		if userinfo.IsSuper == 0 && !excludeAuthPath(string(urlPath.Path)) {
			roleId := userinfo.RoleId
			roleAuth := []models.RoleAuth{}
			models.DB.Where("role_id=?", roleId).Find(&roleAuth)
			roleAuthMap := make(map[int]int)
			for _, v := range roleAuth {
				roleAuthMap[v.AuthId] = v.AuthId
			}
			auth := models.Auth{}
			models.DB.Where("url=?", urlPath.Path).Find(&auth)
			if _, ok := roleAuthMap[auth.Id]; !ok {
				ctx.WriteString("没有权限")
				return
			}
		}
	}
}

//检验路径权限
func excludeAuthPath(urlPath string) bool {
	excludeAuthPath, _ := beego.AppConfig.String("excludeAuthPath")
	excludeAuthPathSlice := strings.Split(excludeAuthPath, ",")
	for _, v := range excludeAuthPathSlice {
		if v == urlPath {
			return true
		}
	}
	return false
}
