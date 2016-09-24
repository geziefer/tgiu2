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
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@Named
@SessionScoped
public class PlayersController implements Serializable {
	private static final long serialVersionUID = 2694864640991849406L;

	private List<Player> players = new ArrayList<>();

	@PostConstruct
	public void populateList() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
		players = query.getResultList();
	}

	public List<Player> getPlayers() {
		return players;
	}
}