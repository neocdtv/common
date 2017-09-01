# streamingservice
This project allows you to stream you local files over http. No security constraints, so please watch out for your data.
##Install
Check out the project, build with maven and use in your project:
```xml
<dependencies>
  <dependency>
    <groupId>io.neocdtv</groupId>
    <artifactId>StreamingService</artifactId>
    <version>1.0-SNAPSHOT</version>
  </dependency>
</dependencies>
```
##Usage
```java
StreamingService.start();
```
or with port
```java
StreamingService.start(1234);
```
