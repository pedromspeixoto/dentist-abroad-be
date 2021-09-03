# Change base directory
cd ../../

# Build All Modules
cd da.submission-microservice/
mvn clean package
cd ..

# Change base directory
cd deployment/local/

# Run Docker Compose
docker-compose up -d --build