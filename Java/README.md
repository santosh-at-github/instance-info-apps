# java-instance-info-app

## Prerequisite
1. Java 11+
2. Maven 3.9.3+
3. docker

## Instruction to use
1. Clone the repo
    ```
    git clone https://github.com/santosh-at-github/instance-info-apps.git
    ```
2. Change directory to app base directory.
    ```
    cd instance-info-apps/Java/java-instance-info-app
    ```
3. Package the Java App.
    ```
    mvn clean package
    ```
4. Run the application
    ```
    java -jar target/webapp-example-1.0-SNAPSHOT.jar 
    ```
5. To containerized the application, execute below command.
    ```
    docker build -t java-instance-info-app:latest
    ```
6. To run the containerized app, execute below.
    ```
    docker run -p 8080:8080 java-instance-info-app:latest
    ```
