CREATE TABLE directors (
	director_id SERIAL Primary key,
	first_name varchar(30),
	last_name varchar(30) not null,
	date_of_birth DATE,
	nationality varchar(20)
);

create table person (
    id bytea primary key,
    name varchar(30),
    password varchar(30)
)