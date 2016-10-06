package geziefer.tgiu2.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({ @NamedQuery(name = "Round.findAll", query = "SELECT r FROM Round r ORDER BY r.date DESC"),
		@NamedQuery(name = "Round.findByDate", query = "SELECT r FROM Round r WHERE r.date >= :from AND r.date <= :to") })
public class Round extends Base {
	@Column
	private LocalDate date;

	@OneToOne
	@JoinColumn(name = "game_id")
	private Game game;

	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Rank> ranks;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
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

	public boolean checkPlayer(String name) {
		return ranks.stream().anyMatch(r -> r.getPlayer().getName().equals(name));
	}

	public Double getPlayerPoints(String name) {
		Optional<Rank> rank = ranks.stream().filter(r -> r.getPlayer().getName().equals(name)).findFirst();
		return rank.isPresent() ? rank.get().calculateValue() * game.getValue().value : 0.0;
	}
}
