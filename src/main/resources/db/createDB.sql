create table Config(
	name varchar(50) not null,
	value varchar(100),
	primary key (name)
);

create table Player(
	id SERIAL,
	name varchar(20) not null,
	password varchar(42) not null,
	role varchar(10) not null default 'USER',
	primary key (id)
);

alter table Player
add unique (name);

create table Game(
	id SERIAL,
	name varchar(50) not null,
	value varchar(10) not null,
	primary key (id)
);

alter table Game
add unique (name);

create table Comment(
	id SERIAL,
	game_id integer not null,
	player_id integer not null,
	comment varchar(250),
	primary key (id),
	foreign key (game_id) references Game (id),
	foreign key (player_id) references Player (id)
);

create table Round(
	id SERIAL,
	date date not null,
	game_id integer not null,
	deleted boolean not null default true,
	primary key (id),
	foreign key (game_id) references Game (id)
);

<<<<<<< HEAD
create table `Rank`(
	id integer not null auto_increment,
=======
create table Rank(
	id SERIAL,
>>>>>>> wildfly
	round_id integer not null,
	player_id integer not null,
	rank integer not null,
	primary key (id),
	foreign key (round_id) references `Round` (id),
	foreign key (player_id) references Player (id)
);

