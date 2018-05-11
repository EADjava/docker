FROM base/java8:1.0
MAINTAINER 徐军奇<junqixu@jieyuechina.com>
LABEL Description="esb boot uuid images "  Version="1.0"
ADD uuid-1.0.0.jar /tmp
#ADD application.properties /tmp
#ADD logback.xml /tmp
#EXPOSE 10017
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=128m"
#ENTRYPOINT ["java","-jar","/tmp/uuid-1.0.0.jar"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /tmp/uuid-1.0.0.jar " ]