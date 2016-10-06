package geziefer.tgiu2.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Ranking;
import geziefer.tgiu2.entity.Round;

@Named
@SessionScoped
public class OverviewController implements Serializable {
	private static final long serialVersionUID = 6382467439487851769L;

	private List<Ranking> rankings = new ArrayList<>();

	private List<Round> rounds = new ArrayList<>();

	private List<Player> players = new ArrayList<>();

	public void initFields() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		LocalDate from = LocalDate.now().withDayOfYear(1);
		LocalDate to = from.plusYears(1).minusDays(1);
		TypedQuery<Round> query1 = em.createNamedQuery("Round.findByDate", Round.class);
		query1.setParameter("from", from);
		query1.setParameter("to", to);
		rounds = query1.getResultList();
		TypedQuery<Player> query2 = em.createNamedQuery("Player.findAll", Player.class);
		players = query2.getResultList();

		rankings = new ArrayList<>();
		for (Player player : players) {
			Ranking ranking = new Ranking();
			ranking.setName(player.getName());
			ranking.setRounds((int) rounds.stream().filter(r -> r.checkPlayer(player.getName())).count());
			ranking.setSum(rounds.stream().mapToDouble(r -> r.getPlayerPoints(player.getName())).sum());
			ranking.setScore(ranking.getRounds() == 0 ? 0 : ranking.getSum() / ranking.getRounds());
			rankings.add(ranking);
		}
	}

	public List<Ranking> getRankings() {
		return rankings;
	}

	public List<Round> getRounds() {
		return rounds;
	}

}