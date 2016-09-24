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
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@Named
@SessionScoped
public class GamesController implements Serializable {
	private static final long serialVersionUID = -5095638556476107999L;

	private List<Game> games = new ArrayList<>();

	@PostConstruct
	public void populateList() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Game> query = em.createNamedQuery("Game.findAll", Game.class);
		games = query.getResultList();
	}

	public List<Game> getGames() {
		return games;
	}
}