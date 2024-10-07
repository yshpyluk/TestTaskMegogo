### Requirements
* Java 17
* Lombok plugin
* Lombok annotation processing is enabled

### run command:
`mvn clean test`

### Parallel execution through surefire plugin
```
<configuration>
    <parallel>classes</parallel>
    <threadCount>2</threadCount>
</configuration>
```

### Report:
Generate: `mvn allure:report` <p>
Generate and open in browser: `mvn allure:serve`

### Docker:
Build image: `docker build -t test-task-megogo-tests .` <p>
Run image with tests: `docker run test-task-megogo-tests`