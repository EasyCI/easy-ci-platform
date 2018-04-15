# EasyCI 后端服务

> EasyCI 是一个简易的持续集成服务，帮助软件开发人员快速的实施持续集成，实现编译、测试、打包、发布全流程自动化。

以下为 EasyCI 后端服务部署流程，更多帮助内容请参阅 **[EasyCI 帮助文档](https://github.com/EasyCI/easy-ci-doc/blob/master/README.md)**

## （一）准备

### 运行环境

- Java 1.8+
- MySQL 5.6+

### 工具

- Git
- Maven 3.0+

## （二）配置

### 配置数据库

后端程序需要一个数据库，可以执行以下语句在 MySQL 中创建所需的数据库

```
mysql -u <your-database-user-name> -p -e "CREATE DATABASE easy_ci_platform CHARACTER SET utf8 COLLATE utf8_bin;"
```

### 获取主程序

通过以下命令，获取源代码

```
git clone -b master https://github.com/EasyCI/easy-ci-platform.git
```

按照以下说明修改配置文件

```
cd ./easy-ci-platform/src/main/resources/
ls *.yml
```

找到上述目录下的 `application.yml` 配置文件，将下方示例中给出注释的条目按照自己的情况进行修改

```
# System Configuration
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/easy_ci_platform?useSSL=false&useUnicode=true&characterEncoding=utf-8   # 数据库地址，请保留“？”后的参数
    username: root   # 数据库账户名
    password: root   # 数据库密码
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true   # 控制台是否打印 SQL 语句， true|false
  jackson:
    date-format: yyyy-MM-dd HH:mm:ss
    time-zone: GMT+8
  mvc:
    view:
      prefix: classpath:/templates/
      suffix: .html
server:
  port: 8080   # 后端服务程序启动端口
  context-path: /

# Custom Configuration
custom:
  serverHost: http://6629e695.ngrok.io   # 后端服务程序部署的服务器主机地址（需要具有公网访问权限！）
  githubClientId: 384bd7c1472f8f66807d
  githubClientSecret: 8dbb5c28d0bdcac525e0ad13c508442fbb91a3e9
  githubAuthorizationScopes: user:email,repo
  pluginScriptPath: /home/lpy/IdeaProjects/easy-ci-plugin/script/   # EasyCI 插件脚本放置位置（需要填写绝对路径）
```

通过以下命令，编译生成 jar 包，结果输出在 ./target 文件下

```
cd ../../../   # 退回到 src/ 同级目录下
mvn clean package -DskipTests=true
cd ./target/
ls *.jar
```

## （三）运行

可通过 `java` 命令直接运行上一步编译生成的 jar 包程序

```
java -jar your-app.jar
```

默认通过 8080 端口启动（依据程序编译前配置文件中 server.port 项参数），若需要从其它端口启动可添加 `--server.port` 参数，例如：

```
java -jar your-app.jar --server.port=8090
```

如果控制台没有报错，可在浏览器中输入 `http://localhost:8080` 进行验证，若看到 **Hello!** 字样，表明服务启动成功！

## （四）其它

### 关于运行环境

EasyCI 后端服务程序，除了直接通过 `java` 命令运行，还可以部署在常规 Web 服务器上，例如 Tomcat，具体部署方式，请查阅相关文档。

### 关于网络

为获取 GitHub 的授权以及实现 `git push` 动作触发我们的 EasyCI 自动构建机制，EasyCI 需要部署在能够具有公网访问权限的环境下，若在本机尝试部署 EasyCI，且网络环境不具备公网 IP，可使用端口映射工具，这里推荐一个小工具 **[ngrok](https://ngrok.com/)**