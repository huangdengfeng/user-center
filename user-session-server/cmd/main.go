package main

import (
	"context"
	"dubbo.apache.org/dubbo-go/v3/common/extension"
	dubbo "dubbo.apache.org/dubbo-go/v3/config"
	_ "dubbo.apache.org/dubbo-go/v3/imports"
	log "github.com/sirupsen/logrus"
	"os"
	"time"
	"user-session-server/entity/config"
	"user-session-server/repo/pb"
	"user-session-server/service"
)

func main() {
	//初始化自定义配置
	config.Init()
	// dubbo 配置
	dubbo.SetProviderService(&service.UserSessionService{})
	client := &pb.UserSessionServiceClientImpl{}
	dubbo.SetConsumerService(client)
	if err := dubbo.Load(dubbo.WithPath(config.ServerConfigPath + string(os.PathSeparator) + "dubbogo.yaml")); err != nil {
		log.Fatal(err)
	}
	time.Sleep(10 * time.Second)
	// 自己调自己测试
	if resp, err := client.Create(context.Background(), &pb.CreateSessionReq{}); err == nil {
		log.Infof("resp:%v", resp)
	} else {
		log.Errorf("err:%v", err)
	}

	stop := make(chan int32)
	extension.AddCustomShutdownCallback(func() {
		stop <- 1
	})
	select {
	case <-stop:
		log.Info("Application Stopped")
	}

	//sessionDao := dao.NewSessionDao()
	//session := &dao.Session{
	//	Uid:       1,
	//	LoginType: dao.MobileLogin,
	//}
	//err := sessionDao.Save("test", time.Hour*2, session)
	//if err != nil {
	//	panic(err)
	//}
	//v, err := sessionDao.Get("test")
	//if err != nil {
	//	panic(err)
	//}
	//log.Infof("%+v", v)

}
