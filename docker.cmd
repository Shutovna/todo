docker build -t shutovna/todo .
docker network create todo-network
docker run -dp 5432:5432 --net todo-network --name todo-db -e POSTGRES_PASSWORD=password postgres
docker run -d -p 8080:8080 --rm --net todo-network --name todo-web shutovna/todo
docker exec -i -t todo-web bash