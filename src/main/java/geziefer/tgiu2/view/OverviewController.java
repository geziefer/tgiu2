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
import geziefer.tgiu2.entity.Ranking;
import geziefer.tgiu2.entity.Round;

@Named
@SessionScoped
public class OverviewController implements Serializable {
	private static final long serialVersionUID = 6382467439487851769L;

	private List<Ranking> rankings = new ArrayList<>();

	private List<Round> rounds = new ArrayList<>();

	public void initFields() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		LocalDate from = LocalDate.now().withDayOfYear(1);
		LocalDate to = from.plusYears(1).minusDays(1);
		TypedQuery<Round> query = em.createNamedQuery("Round.findByDate", Round.class);
		query.setParameter("from", from);
		query.setParameter("to", to);
		rounds = query.getResultList();

		Ranking ranking = new Ranking();
		ranking.setName("Alex");
		ranking.setRounds(5);
		ranking.setSum(75.3);
		ranking.setScore(75.3 / 5);
		rankings.add(ranking);
	}

	public List<Ranking> getRankings() {
		return rankings;
	}

	public List<Round> getRounds() {
		return rounds;
	}

}