insert into tgiu.game (name, value) 
select name, 
	case when wert = 0.0 then "SMALL" 
		when wert = 1.0 then "SMALL" 
		when wert = 2.0 then "MEDIUM" 
		when wert = 3.0 then "LARGE" 
	end 
from tgiu_old.spiel;

insert into tgiu.player (name, password) 
select name, passwort 
from tgiu_old.spieler;

insert into tgiu.round (date, game_id) 
select datum, 
	(select id 
	from tgiu.game 
	where name = spiel)
from tgiu_old.spielrunde;

insert into tgiu.rank (round_id, player_id, rank) 
select  
	(select id 
	from tgiu.round 
	where date = datum 
		and game_id = 
			(select id 
			from tgiu.game 
			where name = spiel)),
	(select id 
	from tgiu.player 
	where name = spieler),
    platz
from tgiu_old.platzierung;
