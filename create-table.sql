CREATE table users (
	id int auto_increment,
	name varchar(100) not null,
	password varchar(255) not null,
	hierarchical_user int,
	password_score int,
	password_weight varchar(30),
	primary key (id)
)