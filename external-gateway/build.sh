#!/bin/sh
set -e
WORK_DIR=$(cd "$(dirname $0)" && pwd)
cd "${WORK_DIR}"
# 1、打包
version=$(mvn -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive exec:exec -q)
artifactId=$(mvn -Dexec.executable='echo' -Dexec.args='${project.artifactId}' --non-recursive exec:exec -q)
mvn clean package -DskipTests

#2、制作镜像 & 推送 需要提前有docker仓库group
cd ${WORK_DIR}
group=734839030
docker build --build-arg MODULE_NAME=${artifactId} -t "${group}/${artifactId}":"${version}" .
# 删除被覆盖的，这里需要先停止相关容器，正常镜像机器不会启动容器
docker images | grep '<none>.*<none>' | awk '{print $3 }' | xargs docker rmi
#3、推送
#docker push "${group}/${artifactId}":"${version}"

