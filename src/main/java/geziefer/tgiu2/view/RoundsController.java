package geziefer.tgiu2.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Rank;
import geziefer.tgiu2.entity.Round;

@Named
@SessionScoped
public class RoundsController implements Serializable {
	private static final long serialVersionUID = -4061642452157056938L;

	@Inject
	private transient PropertyResourceBundle msg;

	private List<Player> players = new ArrayList<>();

	private List<Game> games = new ArrayList<>();

	private Game game;

	private Date date;

	private List<Rank> ranks = new ArrayList<>();

	private List<Round> rounds = new ArrayList<>();

	public void initFields() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query1 = em.createNamedQuery("Player.findAll", Player.class);
		players = query1.getResultList();
		TypedQuery<Game> query2 = em.createNamedQuery("Game.findAll", Game.class);
		setGames(query2.getResultList());
		TypedQuery<Round> query3 = em.createNamedQuery("Round.findAll", Round.class);
		setRounds(query3.getResultList());

		game = null;
		date = null;
		ranks = new ArrayList<>();
		for (Player player : players) {
			Rank rank = new Rank();
			rank.setPlayer(player);
			rank.setRank(0);
			ranks.add(rank);
		}
	}

	public List<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Round> getRounds() {
		return rounds;
	}

	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}

	public String getPlayersForRank(Round round, int rankNumber) {
		List<Rank> ranks = round.getRanks().stream()
				.filter(r -> rankNumber <= 3 ? r.getRank() == rankNumber : r.getRank() > 3)
				.sorted((r1, r2) -> r1.getPlayer().getName().compareTo(r2.getPlayer().getName()))
				.collect(Collectors.toList());

		StringBuilder result = new StringBuilder();
		String delimiter = "";
		for (Rank rank : ranks) {
			result.append(delimiter).append(rank.getPlayer().getName());
			delimiter = ", ";
		}

		if (result.length() == 0) {
			result.append("-");
		}

		return result.toString();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String formatDate(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return date.format(formatter);
	}

	public String createRound() {
		List<Rank> newRanks = checkAndAdaptRanks(ranks);
		if (newRanks.size() < 3) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getString("rounds.error.players"), ""));
		} else {
			Round newRound = new Round();
			newRound.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			newRound.setGame(game);
			newRanks.stream().forEach(r -> r.setRound(newRound));
			newRound.setRanks(newRanks);

			EntityManager em = LocalEntityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			em.persist(newRound);
			em.getTransaction().commit();
			initFields();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("rounds.info.success"), ""));
		}

		return "";
	}

	// tested by RankTest
	List<Rank> checkAndAdaptRanks(List<Rank> ranks) {
		List<Rank> newRanks = ranks.stream().filter(r -> r.getRank() > 0)
				.sorted((r1, r2) -> Integer.compare(r1.getRank(), r2.getRank())).collect(Collectors.toList());

		int last = 0;
		int count = 1;
		int add = 0;
		for (Rank rank : newRanks) {
			int current = rank.getRank() + add;
			if (current == last + count || current == last) {
				if (current == last) {
					count++;
				} else {
					count = 1;
				}
			} else {
				add += last + count - current;
				current = last + count;
				count = 1;
			}
			rank.setRank(current);
			last = current;
		}

		return newRanks;
	}
}