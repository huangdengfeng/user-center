package dao

import (
	"fmt"
	"github.com/redis/go-redis/v9"
	"reflect"
	"testing"
	"time"
)

func TestHelloWord(t *testing.T) {
	//config.ServerConfigPath = "/Users/huangdengfeng/Documents/personal/architecture/codes/user-center/user-session-server/conf"
	fmt.Printf("hello test")
}

func TestNewSessionDao(t *testing.T) {
	tests := []struct {
		name string
		want SessionDao
	}{
		{"NewSessionDao", NewSessionDao()},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			if got := NewSessionDao(); !reflect.DeepEqual(got, tt.want) {
				t.Errorf("NewSessionDao() = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_sessionDaoImpl_Get(t *testing.T) {
	type fields struct {
		redisClient *redis.Client
	}
	type args struct {
		sessionId string
	}
	tests := []struct {
		name    string
		fields  fields
		args    args
		want    *Session
		wantErr bool
	}{
		// TODO: Add test cases.
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			s := &sessionDaoImpl{
				redisClient: tt.fields.redisClient,
			}
			got, err := s.Get(tt.args.sessionId)
			if (err != nil) != tt.wantErr {
				t.Errorf("Get() error = %v, wantErr %v", err, tt.wantErr)
				return
			}
			if !reflect.DeepEqual(got, tt.want) {
				t.Errorf("Get() got = %v, want %v", got, tt.want)
			}
		})
	}
}

func Test_sessionDaoImpl_Save(t *testing.T) {
	type fields struct {
		redisClient *redis.Client
	}
	type args struct {
		sessionId  string
		expiration time.Duration
		session    *Session
	}
	tests := []struct {
		name    string
		fields  fields
		args    args
		wantErr bool
	}{
		// TODO: Add test cases.
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			s := &sessionDaoImpl{
				redisClient: tt.fields.redisClient,
			}
			if err := s.Save(tt.args.sessionId, tt.args.expiration, tt.args.session); (err != nil) != tt.wantErr {
				t.Errorf("Save() error = %v, wantErr %v", err, tt.wantErr)
			}
		})
	}
}
