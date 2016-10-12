package geziefer.tgiu2.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.PieChartModel;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.GameValue;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Rank;

@Named
@SessionScoped
public class StatisticsController implements Serializable {
	private static final long serialVersionUID = -270705952015361605L;

	@Inject
	private transient PropertyResourceBundle msg;

	private List<Rank> ranks = new ArrayList<>();

	private List<Player> players = new ArrayList<>();

	private Long roundCount;

	public void initFields() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		// TODO: depending on combobox selection
		LocalDate from = LocalDate.now().withDayOfYear(1);
		LocalDate to = from.plusYears(1).minusDays(1);
		TypedQuery<Rank> query1 = em.createNamedQuery("Rank.findByDate", Rank.class);
		query1.setParameter("from", from);
		query1.setParameter("to", to);
		ranks = query1.getResultList();

		TypedQuery<Player> query2 = em.createNamedQuery("Player.findAll", Player.class);
		players = query2.getResultList();

		roundCount = ranks.stream().filter(distinctByKey(r -> r.getRound().getId())).count();
	}

	public HorizontalBarChartModel getRounds() {
		HorizontalBarChartModel model = new HorizontalBarChartModel();
		model.setTitle(msg.getString("statistics.chart.rounds.title"));
		model.setDatatipFormat("%d");
		ChartSeries series = new ChartSeries();
		for (int i = players.size() - 1; i >= 0; i--) {
			Player player = players.get(i);
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName())).count();
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
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() == 1)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getSecondRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.secondranks.title"));
		model.setLegendPosition("e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
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
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> (r.getRank() == 3)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getDNFRanks() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.dnfranks.title"));
		model.setLegendPosition("e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
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
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> r.getRound().getGame().getValue().equals(GameValue.LARGE)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	public PieChartModel getBestMedium() {
		PieChartModel model = new PieChartModel();
		model.setTitle(msg.getString("statistics.chart.bestlarge.title"));
		model.setLegendPosition("e");
		model.setLegendPlacement(LegendPlacement.OUTSIDEGRID);
		model.setShowDataLabels(true);
		for (Player player : players) {
			Long count = ranks.stream().filter(r -> r.getPlayer().getName().equals(player.getName()))
					.filter(r -> r.getRound().getGame().getValue().equals(GameValue.MEDIUM)).count();
			model.set(player.getName(), count);
		}

		return model;
	}

	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public Long getRoundCount() {
		return roundCount;
	}
}