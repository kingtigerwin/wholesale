create table users (
    id serial primary key,
    user_id varchar(30),
    encoded_password varchar(100)
);

create table authorities (
    id serial primary key,
    permission varchar(30)
);

create table users_authorities (
    user_id int references users (id),
	authority_id int references authorities (id),
	primary key (user_id, authority_id)
);
