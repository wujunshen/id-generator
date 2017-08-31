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