# running-information-analysis-service
CS504 first homework

国内的小渣渣表示，踩了无数的坑终于把第一次作业完成了。毕竟曾经只用过win98/win7，现在用Linux，感谢BitTiger打开了我新世界的大门......（踩坑过程全程记录，欢迎交流）。

写这个之前先去研究了下[如何编写README.md](https://guides.github.com/features/mastering-markdown/)，不想看英文的看[这里](https://github.com/guodongxiaren/README)

很感谢有[满分作业](https://github.com/xujiahaha/CS504-running-information-analysis-service)带路，写的真的好啊，不要介意我抄哈:joy: 如果要收版费我交啊，好了开始写正题。

***
**running-information-analysis-service** is a [RESTful](http://www.cnblogs.com/loveis715/p/4669091.html) service in spring boot, using [Maven](https://maven.apache.org/) as build tool.

**Feature List:**

* Upload runningInfo List.
* Delete all runningInfo.
* Delete runningInfo by runningId.
* Find runningInfo by runningId.
* Get all runningInfo that belong to one user. (Find runningInfo by username, order by heartRate in descending order)
* Get all runningInfo with sort and pagination. Page number, page size, sort direction and sort property can be customized.

## Requirements

* Java Platform (JDK) 8
* Apache Maven
* Docker
* Docker Compose
> 可以通过查看版本来确认以上软件有无安装，查看命令以及我的版本号如下：<br>
> java -version ==> java version "1.8.0_131"<br>
> mvn --version ==> Apache Maven 3.0.5<br>
> docker --version ==> Docker version 1.9.1<br>
> docker-compose --version ==> docker-compose version: 1.3.1<br>
> 前三个是我使用老师集成的代码安装的，docker compose可能因为国内连接不稳定，是后来装的，参考[Docker中文指南](http://www.widuu.com/docker/compose/install.html)

## Installation and Quick Start
#### 1.Download project file
    git clone https://github.com/15915763299/running-information-analysis-service.git
#### 2.Use Docker to run MySQL server
>先在Terminal中进入刚下载好的文件夹<br>

    cd CS504-running-information-analysis-service
>然后使用一下命令执行`docker-compose.yml`，“-d”是在后台执行的意思，至于这个文件里的代码意义，到[Docker官网](https://docs.docker.com/get-started/)将前三步弄懂就差不多了<br>

    docker-compose up -d
>最后使用以下命令确认sql的image已经在container中运行（看不懂这句赶快去看官网）

    docker ps
#### 3.Login MySQL database inside the Docker container
>运行 `docker ps` 获取 container 名称，将这个名称替换以下代码中的 `containerName`，然后运行代码，输入密码`password`，这个密码是在`docker-compose.yml`中定义的。

    docker exec -ti containerName mysql -uroot -p
>登录sql成功后，依次执行以下命令，创建数据库与表

    mysql> SHOW DATABASES;
    mysql> USE runningInfoAnalysis_db;
    mysql> SHOW TABLES;
    mysql> CREATE TABLE RUNNING_ANALYSIS (id BIGINT(20) AUTO_INCREMENT, runningId VARCHAR(50), latitude DOUBLE, longitude DOUBLE, runningDistance DOUBLE, totalRunningTime DOUBLE, timestamp TIMESTAMP, healthWarningLevel INT, heartRate INT, userName VARCHAR(30), userAddress VARCHAR(50), PRIMARY KEY(id));
    mysql> EXIT;
    
#### 4.Build and run Spring Boot application
>Change directory to `.../CS504-running-information-analysis-service` before running the following commands

    mvn clean install
    java -jar ./target/running-information-analysis-service-1.0.0.BUILD-SNAPSHOT.jar
    
#### 5.Upload runningInfo.json data
>Run the command uhder the directory `.../CS504-running-information-analysis-service`.

    curl -H "Content-Type: application/json" localhost:8080/runningInfo -d @runningInfo.json
>你也可以通过Chrome插件Postman来将Json数据插入数据库（在Chrome的App里查找），最后我是使用这个插件进行测试的，当然你也可以去[Psotman官网](https://www.getpostman.com/apps)直接下载安装。



<br><br><br>
## API Overview
    Check API References for detailed information
Method | URL | Description
------------ | ------------- | -------------
Post | /runningInfo/uploadInfoList | upload a list of runningInfo
Delete | /runningInfo/purge | delete all runningInfo
Delete | /runningInfo/{runningId} | delete one runningInfo by runningId
Get | /runningInfo/findByRunningId/{runningId} | get one runningInfo by runningId
Get | /runningInfo/findByUsername/{username} | get all runningInfo by username, order by heartRate. desc.
Get | /runningInfo | get all runningInfo with pagination and sort


## API References
#### 1.upload a list of runningInfo
   URL: `/runningInfo/uploadInfoList`<br>
   HTTP Method: **POST**<br>
   Success Response: 201 (CREATE)<br>
  
Data Example:<br>

    [
        {
            "runningId": "7c08973d-bed4-4cbd-9c28-9282a02a6032",
            "latitude": "38.9093216",
            "longitude": "-77.0036435",
            "runningDistance": "39492",
            "totalRunningTime": "2139.25",
            "heartRate": 0,
            "timestamp": "2017-04-01T18:50:35Z",
            "userInfo": {
                "username": "ross0",
                "address": "504 CS Street, Mountain View, CA 88888"
            }
        }
    ]

#### 2.delete all runningInfo
   URL: `/runningInfo/purge`<br>
   HTTP Method: **DELETE**<br>
   Success Response: 200 (OK)<br>
  
#### 3.delete runningInfo by runningId
   URL: `/runningInfo/{runningId}`<br>
   HTTP Method: **DELETE**<br>
   URL Params: runningId **(required)**<br>
   Success Response: 200 (OK)<br>
  
Example:<br>

    /runningInfo/fb0b4725-ac25-4812-b425-d43a18c958bb
  
#### 4.Find runningInfo by runningId
   URL: `/runningInfo/findByRunningId/{runningId}`<br>
   HTTP Method: **GET**<br>
   URL Params: runningId **(required)**<br>
   Success Response: 200 (OK)<br>
   Error Response: 404 (Not Found)<br>
  
  Example:<br>

    /runningInfo/findByRunningId/fb0b4725-ac25-4812-b425-d43a18c958bb
    
#### 5.Find runningInfo by username
   URL: `/runningInfo/findByUsername/{usename}`<br>
   HTTP Method: **GET**<br>
   URL Params: usename **(required)**<br>
   Success Response: 200 (OK)<br>
  
   Optional URL Params:<br>
   
Param|Optional|Description|Default Value|Example
------------ | ------------- | ------------- | ------------- | -------------
page|yes|set page number|0|page=1
size|yes|set page size|2|size=5
  
  
  Example:<br>

    /runningInfo/findByUsername/ross0
    /runningInfo/findByUsername/ross0?page=0
    /runningInfo/findByUsername/ross0?page=0&size=3
    
  Sample Response:<br>
  
    [
        {
            "runningId": "2f3c321b-d239-43d6-8fe0-c035ecdff232",
            "totalRunningTime": 85560.23,
            "heartRate": 189,
            "userName": "ross2",
            "userAddress": "504 CS Street, Mountain View, CA 88888",
            "healthWarningLevel": "HIGH"
        },
        {
            "runningId": "2f3c321b-d239-43d6-8fe0-c035ecdff231",
            "totalRunningTime": 85431.23,
            "heartRate": 179,
            "userName": "ross2",
            "userAddress": "504 CS Street, Mountain View, CA 88888",
            "healthWarningLevel": "HIGH"
        }
    ]
    
#### 6.Find all runningInfo with sort and pagination
   URL: `/runningInfo`<br>
   HTTP Method: **GET**<br>
   URL Params: usename **(required)**<br>
   Success Response: 200 (OK)<br>
  
   Optional URL Params:<br>
   
Param | Optional | Description | Default Value | Example
------------ | ------------- | ------------- | ------------- | -------------
page | yes | set page number | 0 | page=1
size | yes | set page size | 2 | size=5
sortDir | yes | set sort direction (asc or desc) | desc | sortDir=asc
sortBy | yes | set sort property | heartRate | sortBy=totalRunningTime


  Example:<br>

    /runningInfo
    /runningInfo?page=1
    /runningInfo?page=1&size=4
    /runningInfo?page=1&size=4&sortDir=asc
    /runningInfo?page=1&size=4&sortDir=asc&sortBy=totalRunningTime
    
  Sample Response:<br>
  
    [
        {
            "runningId": "07e8db69-99f2-4fe2-b65a-52fbbdf8c32c",
            "totalRunningTime": 3011.23,
            "heartRate": 198,
            "userName": "ross1",
            "userAddress": "504 CS Street, Mountain View, CA 88888",
            "healthWarningLevel": "HIGH"
        },
        {
            "runningId": "fb0b4725-ac25-4812-b425-d43a18c958bb",
            "totalRunningTime": 123,
            "heartRate": 189,
            "userName": "ross4",
            "userAddress": "504 CS Street, Mountain View, CA 88888",
            "healthWarningLevel": "HIGH"
        }
    ]
  
    
