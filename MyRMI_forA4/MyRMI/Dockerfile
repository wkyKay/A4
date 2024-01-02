# 使用包含Maven和JDK的基础镜像
FROM maven:3.8.1-jdk-8

# 将工作目录设置为`/app`
WORKDIR /app

# 复制pom.xml文件和源码到工作目录
COPY . .

# 构建应用
RUN apt-get update && apt-get install -y netcat
RUN mvn clean package



# 指定启动时的命令，这里假设构建的jar文件名为myrmi-server.jar
CMD ["java", "-cp", "target/classes", "myrmi.demo.Client"]
