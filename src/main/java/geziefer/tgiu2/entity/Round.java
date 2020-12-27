package geziefer.tgiu2.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import geziefer.tgiu2.LocalDateAdapter;

@Entity
@NamedQuery(name = "Round.findAll", query = "SELECT r FROM Round r WHERE r.deleted = false ORDER BY r.date DESC")
public class Round extends Base {
	@Column
	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	private LocalDate date;

	@OneToOne
	@JoinColumn(name = "game_id")
	private Game game;

	@OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Rank> ranks;

	@Column
	private boolean deleted;
        @Column
	private boolean isRated;

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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
        
        public boolean getIsRated() {
		return isRated;
	}

	public void setIsRated(boolean isRated) {
		this.isRated = isRated;
	}

	public boolean checkPlayer(String name) {
		return ranks.stream().anyMatch(r -> r.getPlayer().getName().equals(name));
	}

	public Double getPlayerPoints(String name) {
		Optional<Rank> rank = ranks.stream().filter(r -> r.getPlayer().getName().equals(name)).findFirst();
		return rank.isPresent() ? rank.get().calculateValue() * game.getValue().value * ranks.size() : 0.0;
	}
}
