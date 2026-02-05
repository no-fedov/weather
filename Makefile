
up:
	docker run --env-file .env --name some-mariadb -p 3306:3306 mariadb:latest