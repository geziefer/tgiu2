package geziefer.tgiu2.view;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Rank;

@Named
@SessionScoped
public class StatisticsController implements Serializable {
	private static final long serialVersionUID = -270705952015361605L;

	private List<Rank> ranks = new ArrayList<>();

	private Long roundCount;

	public void initFields() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		// TODO: depending on combobox selection
		LocalDate from = LocalDate.now().withDayOfYear(1);
		LocalDate to = from.plusYears(1).minusDays(1);
		TypedQuery<Rank> query = em.createNamedQuery("Rank.findByDate", Rank.class);
		query.setParameter("from", from);
		query.setParameter("to", to);
		ranks = query.getResultList();

		roundCount = ranks.stream().filter(distinctByKey(r -> r.getRound().getId())).count();
	}

	public List<Rank> getRoundsPerPlayer() {
		return null;
	}

	private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public Long getRoundCount() {
		return roundCount;
	}
}