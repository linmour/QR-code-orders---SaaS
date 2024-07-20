## 基础镜像java
FROM eclipse-temurin:11-jre

#对时作用
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

#目录是终端默认在此目录下
WORKDIR /test

EXPOSE 12802
#如果是到文件夹后面必须加上“/”,不然会找不到文件，./代表当前目录即是/test目录
COPY ./target/linmour-product.jar ./app/

#linmour-product.jar包就会在根目录/test/app/linmour-product.jar下
CMD java -jar ./app/linmour-product.jar
