pid=$(ps aux | grep "java" | grep -v grep | awk '{print $2}')
if [ "$pid" = "" ]
then
    echo "Begin start blog"
    nohup java -jar -Dspring.profiles.active=prod ./blog-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
    echo "blog start successfully"
else
    echo "kill -9 $pid"
    kill -9 $pid
    STATUS=$?
    if [ $STATUS -eq 0 ]
    then
        echo "Kill previous process successfully."
        echo "Begin restart blog"
        nohup java -jar -Dspring.profiles.active=prod ./blog-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
        echo "blog restart successfully."
    else
        echo "Kill previous process failed."
    fi
fi