create table movies (
	movie_id serial primary key,
	movie_name varchar(50) not null,
	movie_length INT,
	movie_lang varchar(20),
	release_date date,
	age_certificate varchar(5),
	director_id int references directors (director_id)
);

CREATE TABLE actors (
	actor_id SERIAL PRIMARY KEY,
	first_name varchar(30),
	last_name varchar(30),
	gender char(1),
	date_of_birth DATE
);

create table movie_revenues (
	revenue_id serial primary key,
	movie_id int references movies (movie_id),
	domestic_takings numeric(6,2),
	international_takings numeric(6,2)
);

create table movies_actors (
	movie_id int references movies (movie_id),
	actor_id int references actors (actor_id),
	primary key (movie_id, actor_id)
);