# 单点登陆
### 1. 项目结构   
  #### 1.1 sso-code   
  #### 1.2 sso-server   
  #### 1.3 sso-client   
### 2. 使用   
  #### 2.1 项目集成   
   `pom`文件引入`sso-code`
```
	<dependency>
         <groupId>com.oliver</groupId>
         <artifactId>sso-code</artifactId>
         <version>1.0-SNAPSHOT</version>
	</dependency>
```
#### 2.2 配置文件配置（服务端）
```
    server:
      servlet:
        context-path: /sso
    spring:
	  redis:
        host: 192.168.20.196
        database: 0
      sso-server:
        sso-server: true #是否是服务端，客户端集成选择false。默认true
        redis-lease-time: 30 #设置session在redis中的保存时间
        redis-time-unit: minutes #设置时间的单位
        cookie-lease-time: 60 #同上
        cookie-time-unit: minutes
```
#### 2.3 配置文件配置（客户端）
```
	spring:
	  sso:
	    sso-url: http://192.168.0.1:8099/sso
	    exclude-path:
	      - /login
	      - /js/**
	    host-name:
	    host-address:
```
项目引入后code后，访问原项目地址`ps:http://192.168.1.1:8099/index`，此时重定向到sso服务地址，地址示例：`http://192.168.0.1:8099/sso/login?redirect_url=http://192.168.1.1:8099/index`
到登陆页面，此时登陆即可。
##### ps:项目默认用户为 admin/123 用户校验需自行添加




