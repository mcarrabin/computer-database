# MAVEN_MYSQL_NETWORK
# create bridge which we will link jenkins and the mysql/maven dind to
docker network rm $(docker network ls -q)
echo "Building maven/mysql(test) network"
docker network create test_nw
echo "Building Tomcat/mysql(prod) network"
docker network create prod_nw

## JENKINS
# remove all existing containers and images
echo "Removing every existing containers and images"
docker rm -f $(docker ps -aq)
docker rmi -f $(docker images)
#start docker jenkins, use the saved config for jenkins docker and link jenkins container socket with host socket
echo "Running jenkins container"
docker run -tdiv /home/excilys/Bureau/config_jenkins/jenkins_home:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock  --name jenkins mcarrabin/cdb-jenkins

echo "Running Tomcat container"
docker run -tdi -p 8080:8080 -p 50000:50000 --net=prod_nw --name=tomcat tomcat:8.5.0-jre8
#docker exec tomcat -c "sh bin/catalina.sh start"
echo "Running Maven container"
docker run -tdi --name=maven --net=test_nw mcarrabin/docker-maven-jdk
echo "Running Mysql(test) container"
docker run -tdi --name=mysqlTest --net=test_nw mcarrabin/docker-mysql
echo "Running Mysql(prod) container"
docker run -tdi --name=mysqlProd --net=prod_nw mcarrabin/docker-mysql
