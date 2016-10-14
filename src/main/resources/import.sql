insert into Player (id, name, password) values (-1, 'Alex', '64542dbabb81dfd446e0cf4f319567c72ee57c7b');
insert into Player (id, name, password) values (-2, 'Bernd', '3ddb99b6e1f6bd86ec2e2d619846a9cdd31018ed');
insert into Player (id, name, password) values (-3, 'Birger', '40ceddc5ff3a931047a82e2e498be51491481ddc');
insert into Player (id, name, password) values (-4, 'Christine', '7c6dbed8e14ba50cb06dbcdbd1ea197425a627de');
insert into Player (id, name, password) values (-5, 'Ersatz', '0f22f24ea0942f5a0e55e342cb79cdbe3d5a1dde');
insert into Player (id, name, password) values (-6, 'Kai', '3a2e072910f8879a6689cfb328c794990a17c243');
insert into Player (id, name, password) values (-7, 'Michael', 'f8c38b2167c0ab6d7c720e47c2139428d77d8b6a');
insert into Player (id, name, password) values (-8, 'Thorsten', '31c699eacdc45f1a41d2007092acc3c7efdc9f4b');

insert into Game (id, name, value) values (-1, 'Siedler', 'LARGE');
insert into Game (id, name, value) values (-2, 'Maracash', 'MEDIUM');
insert into Game (id, name, value) values (-3, 'Al Cabohne', 'SMALL');

insert into Round (id, date, game_id) values (-1, '2015-10-10', -1);
insert into Round (id, date, game_id) values (-2, '2016-01-22', -2);
insert into Round (id, date, game_id) values (-3, '2016-10-12', -3);

insert into Rank (id, round_id, player_id, rank) values (-1, -1, -1, 1);
insert into Rank (id, round_id, player_id, rank) values (-2, -1, -2, 2);
insert into Rank (id, round_id, player_id, rank) values (-3, -1, -3, 3);
insert into Rank (id, round_id, player_id, rank) values (-4, -1, -4, 4);
insert into Rank (id, round_id, player_id, rank) values (-5, -2, -1, 4);
insert into Rank (id, round_id, player_id, rank) values (-6, -2, -2, 3);
insert into Rank (id, round_id, player_id, rank) values (-7, -2, -3, 2);
insert into Rank (id, round_id, player_id, rank) values (-8, -2, -4, 1);
insert into Rank (id, round_id, player_id, rank) values (-9, -3, -1, 2);
insert into Rank (id, round_id, player_id, rank) values (-10, -3, -2, 1);
insert into Rank (id, round_id, player_id, rank) values (-11, -3, -3, 2);
