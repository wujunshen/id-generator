# 分布式ID生成器（雪花算法SpringBoot版）

 **介绍**
---
分布式ID生成器:

全局唯一ID作为一种唯一标识来区分数据，可用作订单号、用户ID等。ID生成器是生成全局唯一ID的工具，可封装为一种基础服务为其他业务提供服务。因此此项目就是用springboot封装ID生成器，让各种业务系统调用

### **雪花算法**

ID生成算法有很多种，此项目是严格遵循Twitter开源的雪花算法来生成唯一性ID。
它是带有时间戳的全局唯一ID生成算法。有一套固定的ID格式，如下：

    41位的时间戳（精确到毫秒，41位的长度可使用69年）
    10位的机器ID（10位长度最多支持1024个服务器节点部署）
    12位的计数序列号（12位支持每节点每毫秒最多生成4096个序列号）

 **使用**
---
### **启动运行项目**
根目录下执行maven命令

    mvn clean install -DskipTests -Dmaven.test.skip=true 
在id-generate-rest的target目录下找到打包生成的zip包，如下图

![Snip20170901_21.png-69.6kB][1]

找到红线所指的zip包解压，然后放置到本地某一目录下，这里我是放置在我本机电脑根目录下。如下图

![Snip20170901_22.png-14.4kB][2]

切换到终端窗口，进入bin目录，执行start.sh启动脚本
命令如下：

    ./start.sh
    ps -ef |grep restApplication

如果出现如下图情形，则证明项目启动成功

![Snip20170901_24.png-288.7kB][3]

我设置的启动端口为10010，上下文名字为myID

在浏览器窗口输入http://localhost:10010/myID/swagger-ui.html
界面如下

![Snip20170901_19.png-121kB][4]

使用Swagger查看和测试4个springboot封装的API接口，具体介绍Swagger中已有，下文我也会详细截图说明
### **API使用介绍**
4个API接口
#### **实时生成ID**
如图，直接点击“try it out”按钮，不需要输入任何参数，返回long型ID

![Snip20170901_25.png-159.2kB][5]

#### **解析ID**
如图，可用前一接口生成的ID，拷贝到输入框，然后直接点击“try it out”按钮，返回json格式字符串，包括时间戳，机器ID和序列号3个属性值

![Snip20170901_26.png-155.5kB][6]
![Snip20170901_27.png-74.3kB][7]

#### **输入参数生成ID**
输入json格式字符串，包括时间戳，机器ID和序列号3个属性值，返回long型ID。如下图
建议输入解析ID接口返回的json格式字符串，和前一接口是互逆操作

![Snip20170901_28.png-171kB][8]
![Snip20170901_29.png-78.2kB][9]

#### **解析时间戳**
输入时间戳值，返回具体的日期值，格式是yyyy-MM-dd HH:mm:ss。如下图
建议输入此前json格式字符串中时间戳值

![Snip20170901_30.png-165.5kB][10]

 **脚本概述**
---
#### **start.sh**
启动脚本内容如下

    #!/bin/sh
    echo -------------------------------------------
    echo start server
    echo -------------------------------------------
    # 设置项目代码路径
    export CODE_HOME="/id-generate-rest"
    #日志路径
    export LOG_PATH="/Users/wujunshen/Downloads/id-generator"
    mkdir -p $LOG_PATH
    # 设置依赖路径
    export CLASSPATH="$CODE_HOME/classes:$CODE_HOME/lib/*"
    # java可执行文件位置
    export _EXECJAVA="$JAVA_HOME/bin/java"
    # JVM启动参数
    export JAVA_OPTS="-server -Xms8m -Xmx2048m "
    # 服务端端口、上下文、项目根配置
    export SERVER_INFO="-Dserver.port=10010 -Dserver.contextPath=/myID -Dserver.docBase=$CODE_HOME"
    # 启动类
    export MAIN_CLASS=com.wujunshen.rest.RestApplication
    
    nohup $_EXECJAVA $JAVA_OPTS -classpath $CLASSPATH $SERVER_INFO $MAIN_CLASS >$LOG_PATH/id-generator.log  2>&1 &
    
CODE_HOME和LOG_PATH可自行根据zip包解压的目录进行配置修改。包括最后一行的日志文件名，都可修改。
启动类即springboot的Application启动类，相应JVM参数也可根据自身电脑硬件配置做增添修改。还有脚本中端口和上下文名需要和项目中/id-generate-rest/src/main/resources/application.properties文件中的server.port和server.context-path的值一致.

#### **stop.sh**
停止脚本内容如下

    #日志路径
    export LOG_PATH="/Users/wujunshen/Downloads/id-generator"
    mkdir -p $LOG_PATH
    # 启动类
    export MAIN_CLASS=com.wujunshen.rest.RestApplication
    
    echo -------------------------------------------
    echo stop server
    
    #所有相关进程
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    #停止进程
    if [ -n "$PIDs" ]; then
    for PID in $PIDs; do
    kill $PID
    echo "kill $PID"
    done
    fi
    
    #等待50秒
    for i in 1 10; do
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    if [ ! -n "$PIDs" ]; then
    echo "stop server success"
    echo -------------------------------------------
    break
    fi
    echo "sleep 5s"
    sleep 5
    done
    
    #如果等待50秒还没有停止完，直接杀掉
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    if [ -n "$PIDs" ]; then
    for PID in $PIDs; do
    kill -9 $PID
    echo "kill -9 $PID"
    done
    fi

LOG_PATH和MAIN_CLASS和start.sh中配置相一致即可。其它无需做任何改动。

  [1]: http://static.zybuluo.com/coderush/5i3usxwuo9p1dru76b54pyuf/Snip20170901_21.png
  [2]: http://static.zybuluo.com/coderush/1r5ouffvyv8d9zo3ao26jim1/Snip20170901_22.png
  [3]: http://static.zybuluo.com/coderush/mtpgx9qsq79ak5v2wa22njax/Snip20170901_24.png
  [4]: http://static.zybuluo.com/coderush/wst57rxbdymz4p76thx5vbi0/Snip20170901_19.png
  [5]: http://static.zybuluo.com/coderush/x8jal94c2kquzkrz3lf0eowe/Snip20170901_25.png
  [6]: http://static.zybuluo.com/coderush/a457yfhlxkjfwr6oy8aemavo/Snip20170901_26.png
  [7]: http://static.zybuluo.com/coderush/3rz2xhtsri1atztmpbmahhzd/Snip20170901_27.png
  [8]: http://static.zybuluo.com/coderush/r400llmy4tm7kbdm5l099vgg/Snip20170901_28.png
  [9]: http://static.zybuluo.com/coderush/wykjq1qbxxxxurwi0llxpyr3/Snip20170901_29.png
  [10]: http://static.zybuluo.com/coderush/9r4o7hqt4a364hehknlgiix0/Snip20170901_30.png
