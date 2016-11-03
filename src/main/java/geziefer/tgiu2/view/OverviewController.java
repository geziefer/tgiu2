package geziefer.tgiu2.view;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Ranking;
import geziefer.tgiu2.entity.Round;

@Named
@SessionScoped
public class OverviewController implements Serializable {
	private static final long serialVersionUID = 6382467439487851769L;

	@PersistenceContext
	EntityManager em;
	
	@Inject
	private transient PropertyResourceBundle msg;

	@Inject
	private LoginController loginController;

	private List<Ranking> rankings = new ArrayList<>();

	private List<Ranking> filteredRankings = new ArrayList<>();

	private List<Round> rounds = new ArrayList<>();

	private List<Player> players = new ArrayList<>();

	private Integer year;

	private List<Integer> years = new ArrayList<>();

	public void initFields() {
		TypedQuery<Round> query1 = em.createNamedQuery("Round.findAll", Round.class);
		rounds = query1.getResultList();
		TypedQuery<Player> query2 = em.createNamedQuery("Player.findAll", Player.class);
		players = query2.getResultList();

		year = LocalDate.now().getYear();
		years = new ArrayList<>();
		Optional<LocalDate> minDate = rounds.stream().map(r -> r.getDate()).min(LocalDate::compareTo);
		Optional<LocalDate> maxDate = rounds.stream().map(r -> r.getDate()).max(LocalDate::compareTo);
		if (minDate.isPresent() && maxDate.isPresent()) {
			for (int i = maxDate.get().getYear(); i >= minDate.get().getYear(); i--) {
				years.add(i);
			}
		} else {
			years.add(year);
		}

		rankings = new ArrayList<>();
		for (Player player : players) {
			Ranking ranking = new Ranking();
			ranking.setName(player.getName());
			ranking.setRounds((int) rounds.stream().filter(r -> r.checkPlayer(player.getName())).count());
			ranking.setSum(rounds.stream().mapToDouble(r -> r.getPlayerPoints(player.getName())).sum());
			ranking.setScore(ranking.getRounds() == 0 ? 0 : ranking.getSum() / ranking.getRounds());
			rankings.add(ranking);
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				MessageFormat.format(msg.getString("overview.greeting"), loginController.getUsername()), ""));

		changeYear();
	}

	public void changeYear() {
		filteredRankings = new ArrayList<>();
		for (Player player : players) {
			Ranking ranking = new Ranking();
			ranking.setName(player.getName());
			ranking.setRounds((int) rounds.stream().filter(r -> r.checkPlayer(player.getName()))
					.filter(r -> (r.getDate().getYear() == year)).count());
			ranking.setSum(rounds.stream().filter(r -> (r.getDate().getYear() == year))
					.mapToDouble(r -> r.getPlayerPoints(player.getName())).sum());
			ranking.setScore(ranking.getRounds() == 0 ? 0 : ranking.getSum() / ranking.getRounds());
			ranking.setEligible(ranking.getRounds() >= 10);
			filteredRankings.add(ranking);
		}
		List<Ranking> eligibleRanks = filteredRankings.stream().filter(r -> r.isEligible())
				.sorted((r1, r2) -> r2.getScore().compareTo(r1.getScore())).collect(Collectors.toList());
		List<Ranking> ineligibleRanks = filteredRankings.stream().filter(r -> !r.isEligible())
				.sorted((r1, r2) -> r2.getScore().compareTo(r1.getScore())).collect(Collectors.toList());
		filteredRankings = new ArrayList<>();
		filteredRankings.addAll(eligibleRanks);
		filteredRankings.addAll(ineligibleRanks);
	}

	public List<Integer> getYears() {
		return years;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Ranking> getRankings() {
		return rankings;
	}

	public List<Ranking> getFilteredRankings() {
		return filteredRankings;
	}

	public List<Round> getRounds() {
		return rounds;
	}

}