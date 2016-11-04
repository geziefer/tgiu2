create table Comment(
	id integer not null auto_increment,
	game_id integer not null,
	player_id integer not null,
	comment varchar(250),
	primary key (id),
	foreign key (game_id) references Game (id),
	foreign key (player_id) references Player (id)
);

alter table Player
	add role varchar(10) not null default 'USER';
update Player set role = 'ADMIN' where name = 'Alex';
update Player set role = 'ADMIN' where name = 'Thorsten';
	
alter table Round	
	add deleted boolean not null default false;
