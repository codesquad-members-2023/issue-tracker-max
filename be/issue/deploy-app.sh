ISSUE_ID=$(jps | grep issue | awk '{ print $1 }')

if [ -z $ISSUE_ID ]; then
  echo "동작중인 서버가 없습니다."
else
  echo "$ISSUE_ID 프로세스를 삭제합니다."
  kill -9 $ISSUE_ID
fi

echo "서버 시작"
nohup java -jar ~/app/build/libs/issue-0.0.1-SNAPSHOT.jar >/home/ubuntu/app/log.txt 2>&1 &
echo "배포 성공"
