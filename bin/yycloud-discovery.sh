# 启动脚本

#!/bin/sh

# chkconfig: 345 99 10


SERVER_NAME="yy-discovery"
JAR_PATH="/root/yycloud/lib/"$SERVER_NAME"-0.1.0.jar"

PARAMS="spring.profiles.active=${YYCLOUD_PROFILES}" 
EXEC="java -jar $JAR_PATH $PARAMS"
COMMAND=$EXEC
SERVER_TAG="$SERVER_NAME"

echo $STARTUP

is_server_started(){
	ps -ef |grep "$SERVER_TAG" | grep -v grep
	if [ $? -eq 0 ]
	then 
		return 0   #yes
	else 
		return $pid    #no
	fi	
}

start_proc(){
	is_server_started
	if [ $? -eq 0 ]; then
                echo "${SERVER_NAME} is already running !"
        else
                echo "Starting ${SERVER_NAME} ..."
                sleep 2
	       				$EXEC > /dev/null 2>&1 &
		
		is_server_started
		if [ $? -eq 0 ]; then
                	echo "${SERVER_NAME} started !"
		else
                	echo "${SERVER_NAME} starts failed !"
		fi

        fi
}

status_proc(){
	is_server_started
        if [ $? -eq 0 ]; then
                echo "${SERVER_TAG} ${SERVER_NAME} is running !"
        	ps -ef | grep -w "${PROC_TAG}" | grep -v grep
        else
                echo "${SERVER_NAME} is not running !"
        fi
}

stop_proc(){
        is_server_started
	if [ $? -eq 0 ]; then
		pid=`ps -ef|grep ${SERVER_TAG} |grep -v grep|awk '{print $2}'`
		kill -9 ${pid}
		echo "${pid} ${SERVER_NAME} is stoped !"
        else
		echo "${SERVER_NAME} is already stoped !"
        fi
}

client(){
	$COMMAND
}

#=======================================================================
# Main Program begin
#=======================================================================

cd `dirname $0`
case $1 in
        start)
                start_proc
                ;;
        status)
                status_proc
                ;;
        stop)
                stop_proc
                ;;
        restart)
                stop_proc
                start_proc
                ;;
        *)
                client
esac
exit $RETVAL

