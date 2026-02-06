CREATE TABLE IF NOT EXISTS users(
	id int PRIMARY KEY AUTO_INCREMENT,
	login varchar(255) NOT NULL,
	password varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS session(
	id uuid PRIMARY KEY DEFAULT UUID(),
	user_id int NOT NULL,
	expires_at datetime NOT NULL,
	CONSTRAINT session_user_fk
		FOREIGN KEY (user_id)
		REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS location(
	id int PRIMARY KEY AUTO_INCREMENT,
	name varchar(255) NOT NULL,
	latitude decimal NOT NULL,
	longitude decimal NOT NULL,
	CONSTRAINT unique_lat_lon UNIQUE (latitude, longitude)
);

CREATE TABLE IF NOT EXISTS user_location (
	id int PRIMARY KEY AUTO_INCREMENT,
	user_id int NOT NULL,
	location_id int NOT NULL,
	CONSTRAINT user_location_user_fk
		FOREIGN KEY (user_id)
		REFERENCES users(id),
	CONSTRAINT user_location_location_fk
		FOREIGN KEY (location_id)
		REFERENCES location(id),
	UNIQUE unique_location_constraint (user_id, location_id)
);