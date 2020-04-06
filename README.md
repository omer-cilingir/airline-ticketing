


# Airline-ticketing

Projeyi çalıştırmak için projeyi indirdiğiniz dizinde docker-compose komutunu kullanabilirsiniz(Docker yüklü olmalı)

```
$ docker-compose up -d --force-recreate
```
Proje çalıştıktan sonra **docker ps** sonucu olması gereken çıktı aşağıdakine benzer olmalıdır

```
CONTAINER ID        IMAGE                     COMMAND                  CREATED             STATUS              PORTS                    NAMES
523cbc918dee        ticketing_ticketing-app   "java -jar app.jar"      38 seconds ago      Up 37 seconds       0.0.0.0:8080->8080/tcp   ticketing-app
564bff14af0e        postgres:9.6-alpine       "docker-entrypoint.s…"   39 seconds ago      Up 37 seconds       0.0.0.0:5432->5432/tcp   ticketing-postgres
```

Projenin çalıştığını kontrol etmek için ve dökümantasyon olarak swagger adresi kullanılabilir

```
http://localhost:8080/swagger-ui.html
```


Hangi servisin nasıl kullanılacağıyla ilgili postman istekleri **postman_scripts.json** isimli dosyada bulunabilir.

Database tabloları postgres->public altında bulunabilir


* Proje Detayları
  * Spring Boot 2.2.6 versiyonu ile yazılmıştır
  * Database olarak postgresql kullanılmıştır
  * Unit test Junit jupiter ve Mockito ile yazılmıştır
 
 
 
Uygulamayı sonlandırmak için aşağıdaki komut kullanılabilir(Docker kullanılmış ise)
```
$ docker-compose down --rmi all
```



