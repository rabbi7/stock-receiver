## Stock-receiver
Test task for baraka company

## Running application
1. From the root folder run
    ```
    mvn clean package
    ```
2. Build up docker container
    ```
    docker build -t stock-receiver .
    ```
2. Run docker container
    ```
    docker run -p 8080:8080 stock-receiver
    ```
App is run on the port 8080.
You can reach the swagger of the app by link
    ```
    http://127.0.0.1:8080/swagger-ui/index.html
    ```
## Setting up properties
Link to the websocket is setup in src/main/resources/application.yml
You can change it here if you need app to use the other ws endpoint
