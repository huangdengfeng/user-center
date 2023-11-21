package dao

import (
	"context"
	"encoding/json"
	"github.com/redis/go-redis/v9"
	"time"
	"user-session-server/entity/config"
)

// 登录方式
const (
	MobileLogin = 1
	WechatLogin = 2
)

// Session session 内容
type Session struct {
	// 用户ID
	Uid int64
	// 登录时间
	LoginTime time.Duration
	// 登录方式
	LoginType int8
	// 签名
	Signature string
}

type SessionDao interface {
	// Save 保存session
	Save(sessionId string, expiration time.Duration, session *Session) error
	// Get 获取session
	Get(sessionId string) (*Session, error)
}

type sessionDaoImpl struct {
	redisClient *redis.Client
}

func NewSessionDao() SessionDao {
	return &sessionDaoImpl{
		redisClient: config.RedisClient,
	}
}

var ctx = context.Background()

func (s *sessionDaoImpl) Save(sessionId string, expiration time.Duration, session *Session) error {
	marshal, err := json.Marshal(session)
	if err != nil {
		return err
	}
	setErr := s.redisClient.Set(ctx, sessionId, marshal, expiration).Err()
	return setErr
}

func (s *sessionDaoImpl) Get(sessionId string) (*Session, error) {
	result, err := s.redisClient.Get(ctx, sessionId).Bytes()
	if err != nil {
		return nil, err
	}
	var session Session
	err = json.Unmarshal(result, &session)
	if err != nil {
		return nil, err
	}
	return &session, nil
}
