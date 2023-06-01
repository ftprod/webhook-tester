# Usage 

Call the webhook: 

POST http://localhost:8080/webhook-handler
Header (mandatory): x-esante-api-hmac-sha256: sha1=123456
Body: anything

Read the latest 100 calls: 

GET http://localhost:8080/webhook-handler


# Run docker

`docker run -p "8080:8080" ftprod/webhooktester:1.0.1`


# Development

Develop: `mvn compile quarkus:dev`

Package: `mvn package -Pnative -Dquarkus.native.container-build=true -Dquarkus.container-image.build=true`

Create docker image: 

```
docker build -f src/main/Docker/Dockerfile.native -t webhooktester:1.0.1 .
docker tag webhooktester:1.0.1 docker.io/ftprod/webhooktester:1.0.1
docker push docker.io/ftprod/webhooktester:1.0.1
```

