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