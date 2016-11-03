package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment extends Base {
	public Comment() {
	}

	public Comment(Game game, Player player, String comment) {
		this.game = game;
		this.player = player;
		this.comment = comment;
	}

	@OneToOne
	@JoinColumn(name = "game_id")
	@JsonIgnore
	private Game game;

	@OneToOne
	@JoinColumn(name = "player_id")
	private Player player;

	@Column
	private String comment;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
