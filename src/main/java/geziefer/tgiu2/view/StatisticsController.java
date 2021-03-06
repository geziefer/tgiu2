package geziefer.tgiu2.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import geziefer.tgiu2.MyMessageBundle;
import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.GameValue;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Rank;

@Named
@SessionScoped
public class StatisticsController implements Serializable {
	private static final long serialVersionUID = -270705952015361605L;

	@PersistenceContext
	EntityManager em;

	@Inject
	@MyMessageBundle
	private transient PropertyResourceBundle msg;

	@Inject
	private LoginController loginController;

	private List<Rank> ranks = new ArrayList<>();

	private List<Rank> filteredRanks = new ArrayList<>();

	private List<Player> players = new ArrayList<>();

	private Long roundCount;

	private String year;

	private List<String> years = new ArrayList<>();

	private String game;

	private List<String> games;

	public void initFields() {
		TypedQuery<Rank> query1 = em.createNamedQuery("Rank.findAllVisible", Rank.class);
		ranks = query1.getResultList();

		TypedQuery<Player> query2 = em.createNamedQuery("Player.findAll", Player.class);
		players = query2.getResultList();

		years = new ArrayList<>();
		Optional<LocalDate> minDate = ranks.stream().map(r -> r.getRound().getDate()).min(LocalDate::compareTo);
		Optional<LocalDate> maxDate = ranks.stream().map(r -> r.getRound().getDate()).max(LocalDate::compareTo);
		if (minDate.isPresent() && maxDate.isPresent()) {
			for (int i = maxDate.get().getYear(); i >= minDate.get().getYear(); i--) {
				years.add("" + i);
			}
		}
		years.add(msg.getString("statistics.selection.all"));
		year = years.get(0);

		games = new ArrayList<>();
		TypedQuery<Game> query = em.createNamedQuery("Game.findAll", Game.class);
		games.add(msg.getString("statistics.selection.allGames"));
		query.getResultList().forEach(g -> games.add(g.toString()));
		game = games.get(0);

		changeFilter();
	}

	public void changeFilter() {
		Stream<Rank> rankStream = ranks.stream();
		if (!year.equals(msg.getString("statistics.selection.all"))) {
			rankStream = rankStream.filter(
					r -> r.getRound().getDate().isAfter(LocalDate.of(Integer.parseInt(year), 1, 1).minusDays(1)))
					.filter(r -> r.getRound().getDate()
							.isBefore(LocalDate.of(Integer.parseInt(year), 12, 31).plusDays(1)));
		}
		if (!game.equals(msg.getString("statistics.selection.allGames"))) {
			rankStream = rankStream.filter(r -> r.getRound().getGame().getName().equals(game));
		}
		filteredRanks = rankStream.collect(Collectors.toList());
		roundCount = filteredRanks.stream().filter(distinctByKey(r -> r.getRound().getId())).count();
	}

	public Long getRoundCount() {
		return roundCount;
	}

	public List<String> getYears() {
		return years;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public List<String> getGames() {
		return games;
	}

	public HorizontalBarChartModel getRounds() {
		HorizontalBarChartModel model = new HorizontalBarChartModel();
		model.setTitle(msg.getString("statistics.chart.rounds.title"));
		model.setDatatipFormat("%d");
		ChartSeries series = new ChartSeries();
		for (int i = players.size() - 1; i >= 0; i--) {
			Player player = players.get(i);
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName())).count();
			series.set(player.getName(), count);
		}
		series.set(msg.getString("statistics.chart.rounds.total"), roundCount);
		model.addSeries(series);

		return model;
	}

	public PieChartModel getFirstRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.firstranks.title"));
		model.setLegendPosition("w");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() == 1)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getSecondRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.secondranks.title"));
		model.setLegendPosition(loginController.isMobile() ? "w" : "e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() == 2)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getThirdRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.thirdranks.title"));
		model.setLegendPosition("w");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() == 3)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getDNFRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.dnfranks.title"));
		model.setLegendPosition(loginController.isMobile() ? "w" : "e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() > 3)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getBestLarge() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.bestlarge.title"));
		model.setLegendPosition("w");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> r.getRound().getGame().getValue().equals(GameValue.LARGE))
					.filter(r -> (r.getRank() == 1)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getBestMedium() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.bestmedium.title"));
		model.setLegendPosition(loginController.isMobile() ? "w" : "e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = filteredRanks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> r.getRound().getGame().getValue().equals(GameValue.MEDIUM))
					.filter(r -> (r.getRank() == 1)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
}