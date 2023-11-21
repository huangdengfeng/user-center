# 用户Session服务

## go 知识备忘

```shell
# 执行目录下所有test，路径是代表子目录
go test ./...
# 执行当前目录下test,-v 可以详细输出
go test [-v]

# 构建 
# 指定输出路径和文件名
go build [-o path/file] cmd/main.go
# 运行
go run cmd/main.go
```

## dubbo 备忘
dubbo 插件依赖
```shell
go install google.golang.org/protobuf/cmd/protoc-gen-go@latest 
go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
go install github.com/dubbogo/tools/cmd/protoc-gen-go-triple@latest
protoc --go_out=. --go-triple_out=. samples_api.proto
```
