build
```
mvn clean package
```

启动
```shell
nohup java -jar -Dcas.authn.samlIdp.metadata.location=file:/opt/uyun/cas-saml/saml cas-server-1.0-SNAPSHOT.jar &
```
停止
```shell
ps -ef|grep cas-server-1.0-SNAPSHOT.jar
kill -15 {pid}
```

debug启动
```shell
java -jar -Dcas.authn.samlIdp.metadata.location=file:/opt/uyun/cas-saml/saml -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8444 cas-server-1.0-SNAPSHOT.jar
```

# Requirements

* JDK 1.8+
* springframework 4