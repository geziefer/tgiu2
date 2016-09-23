package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@Named
@SessionScoped
public class OverviewController implements Serializable {

	private static final long serialVersionUID = 3893948528151679341L;

	private List<Game> games = new ArrayList<>();
	private List<Player> players = new ArrayList<>();

	@PostConstruct
	public void populateLists() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Game> tq1 = em.createNamedQuery("Game.findAll", Game.class);
		games = tq1.getResultList();
		TypedQuery<Player> tq2 = em.createNamedQuery("Player.findAll", Player.class);
		players = tq2.getResultList();
	}

	public List<Game> getGames() {
		return games;
	}

	public List<Player> getPlayers() {
		return players;
	}
}