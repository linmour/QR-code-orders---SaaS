## 基础镜像java
FROM openjdk
## 作者是linmour
MAINTAINER linmour
## 就是你上传的jar包的名称。给jar包起个别名
ARG JAR_FILE
ADD target/${JAR_FILE} order.jar
## 就是在容器中以多少端口号运行
EXPOSE 12804
## 容器启动之后执行的命令，java -jar demo.jar 即启动jar
ENTRYPOINT ["java","-jar","order.jar"]
