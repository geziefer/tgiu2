package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Rank;
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@Named
@SessionScoped
public class RoundsController implements Serializable {
	private static final long serialVersionUID = -4061642452157056938L;

	private List<Player> players = new ArrayList<>();

	private List<Rank> ranks = new ArrayList<>();

	@PostConstruct
	public void populateList() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
		players = query.getResultList();
	}

	public void initFields() {
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

	public String createRound() {
		for (Rank rank : ranks) {
			System.out.println(rank.getPlayer().getName());
			System.out.println(rank.getRank());
		}

		return "";
	}
}