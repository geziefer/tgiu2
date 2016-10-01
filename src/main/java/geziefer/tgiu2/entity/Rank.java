package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Rank extends Base {
	@ManyToOne
	@JoinColumn(name = "round_id")
	private Round round;

	@OneToOne
	@JoinColumn(name = "player_id")
	private Player player;

	@Column
	private Integer rank;

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

}
