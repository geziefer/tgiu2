package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({ @NamedQuery(name = "Rank.findAll", query = "SELECT r FROM Rank r"),
		@NamedQuery(name = "Rank.findAllVisible", query = "SELECT r FROM Rank r WHERE r.round.deleted = false") })
public class Rank extends Base {
	@ManyToOne
	@JoinColumn(name = "round_id")
	@JsonIgnore
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

	public Double calculateValue() {
		switch (rank) {
		case 1:
			return 1.0;
		case 2:
			return 3.0 / 7.0;
		case 3:
			return 1.0 / 7.0;
		default:
			return 0.0;
		}
	}

}
