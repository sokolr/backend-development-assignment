CREATE TABLE product
(
	id          BIGSERIAL PRIMARY KEY,
	name        VARCHAR(100) NOT NULL CHECK (CHARACTER_LENGTH(name) >= 1),
	description VARCHAR(255),
	price       DECIMAL NOT NULL CHECK (price >= 0)
)
