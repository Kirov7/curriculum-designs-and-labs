package common

import (
	"crypto/md5"
	"encoding/hex"
	"fmt"
	"github.com/beego/beego/v2/core/logs"
	beego "github.com/beego/beego/v2/server/web"
	"github.com/gomarkdown/markdown"
	"github.com/hunterhug/go_image"
	"io/ioutil"
	"math/rand"
	"path"
	"regexp"
	"strconv"
	"strings"
	"time"
)

// TimeStampToDate 时间转换为日期格式
func TimeStampToDate(timestamp int) string {
	t := time.Unix(int64(timestamp), 0)
	return t.Format("2006-01-02 15:04:05")
}

// GetUnix 获取当前时间戳
func GetUnix() int64 {
	fmt.Println(time.Now().Unix())
	return time.Now().Unix()
}

// GetUnixNano 获全国前时间戳的Nano时间
func GetUnixNano() int64 {
	return time.Now().UnixNano()
}

// GetDate 获取当前日期
func GetDate() string {
	template := "2006-01-04 12:01:01"
	return time.Now().Format(template)
}

// Md5 采用md5加密
func Md5(str string) string {
	m := md5.New()
	m.Write([]byte(str))
	return hex.EncodeToString(m.Sum(nil))
}

// VerifyEmail 验证邮箱
func VerifyEmail(email string) bool {
	pattern := `\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*` //匹配电子邮箱
	reg := regexp.MustCompile(pattern)
	return reg.MatchString(email)
}

// FormatDay 获取日期
func FormatDay() string {
	template := "20060102"
	return time.Now().Format(template)
}

// GenerateOrderId 生成订单号
func GenerateOrderId() string {
	template := "200601021504"
	return time.Now().Format(template) + GetRandomNum()
}

// SendMsg 发送验证码
func SendMsg(str string) {
	// 短信验证码需要到相关网站申请
	// 目前先固定一个值
	ioutil.WriteFile("test_send.txt", []byte(str), 06666)
}

// ResizeImage 重新裁剪图片
func ResizeImage(filename string) {
	extName := path.Ext(filename)
	resizeImageSize, _ := beego.AppConfig.String("resizeImageSize")
	resizeImage := strings.Split(resizeImageSize, ",")

	for i := 0; i < len(resizeImage); i++ {
		w := resizeImage[i]
		width, _ := strconv.Atoi(w)
		savepath := filename + "_" + w + "x" + w + extName
		err := go_image.ThumbnailF2F(filename, savepath, width, width)
		if err != nil {
			logs.Error(err)
		}
	}
}

// FormatImage 格式化图片
func FormatImage(picName string) string {
	ossStatus, err := beego.AppConfig.Bool("ossStatus")
	if err != nil {
		//判断目录前面是否有/
		flag := strings.Contains(picName, "/static")
		if flag {
			return picName
		}
		return "/" + picName
	}
	if ossStatus {
		ossDomain, _ := beego.AppConfig.String("ossDomain")
		return ossDomain + "/" + picName
	} else {
		flag := strings.Contains(picName, "/static")
		if flag {
			return picName
		}
		return "/" + picName

	}
}

//格式化级标题
func FormatAttribute(str string) string {
	md := []byte(str)
	htmlByte := markdown.ToHTML(md, nil, nil)
	return string(htmlByte)
}

// Mul 乘法的函数
func Mul(price float64, num int) float64 {
	return price * float64(num)
}

// GetRandomNum 封装一个生产随机数的方法
func GetRandomNum() string {
	var str string
	for i := 0; i < 4; i++ {
		current := rand.Intn(10) //0-9   "math/rand"
		str += strconv.Itoa(current)
	}
	return str
}
