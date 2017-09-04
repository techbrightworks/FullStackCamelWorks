MavenBuild
-----------
1.Go to the Directory FullStackCamelWorks
2.Ensure your JRE and JAVA_HOME is Java 1.8 
3.Use Command: mvn clean install eclipse:clean eclipse:eclipse -Dwtpversion=2.0
4.Use Command: to Skip Tests use -DskipTests
5.Once the  Build  is successful, Refresh the project on your IDE
6.FECamelWorks.war would be now found in the  target Folder of the module FECamelWorks
7a.Note:Please Remember to check the ports chosen in each of module of  parent FullStackCamelWorks are free to use
   Check POM and Route Restconfiguration for Port Info
7b.Go to the Directory FECamelWorks and use the command mvn tomcat7:run-war this would  run with an embedded Apache Tomcat, also helps to quickly develop your application 
   without needing to install a standalone Tomcat instance 
8a.Example url with port 8080 http://localhost:8080/FECamelWorks/camelworks.mvc
8b.Swagger url  http://localhost:8181/api-doc/swagger.json  for DbRestServer
8c.Swagger url  http://localhost:8180/api-doc/swagger.yaml for RLRestServer
9.If you want to view the Java Documentations of FullStackCamelWorks Go to the Directory FullStackCamelWorks
 and Use the command mvn site