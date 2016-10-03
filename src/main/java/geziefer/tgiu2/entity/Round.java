package geziefer.tgiu2.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name = "Round.findAll", query = "SELECT r FROM Round r ORDER BY r.date DESC")
public class Round extends Base {
	@Column
	private Date date;

	@OneToOne
	@JoinColumn(name = "game_id")
	private Game game;

	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Rank> ranks;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}

}
