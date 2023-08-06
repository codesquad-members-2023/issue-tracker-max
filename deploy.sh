#!/bin/sh
REPOSITORY=$HOME/app/issue/BE
PROJECT_NAME=BE
JAR_NAME=$(ls -tr $REPOSITORY/build/libs/ | grep jar)
CURRENT_PID=$(pgrep -f ${PROJECT_NAME})

echo "> 애플리케이션이 현재 실행 중인 경우 종료합니다."
if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 애플리케이션이 실행 중인 상태가 아닙니다."
else
    echo "> kill -15 $CURRENT_PID"
    kill -15 $CURRENT_PID
    sleep 5
fi

echo "> 애플리케이션을 실행합니다."
nohup java -jar $REPOSITORY/build/libs/$JAR_NAME > $REPOSITORY/log.txt 2>&1 &

sudo systemctl restart nginx
