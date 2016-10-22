package geziefer.tgiu2.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Round;

@Path("/")
public class TGIUService {
	@GET
	@Path("/players")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Player> getPlayers() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
		return query.getResultList();
	}

	@GET
	@Path("/games")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Game> getGames() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Game> query = em.createNamedQuery("Game.findAllEagerly", Game.class);
		return query.getResultList();
	}

	@GET
	@Path("/rounds")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Round> getRounds() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Round> query = em.createNamedQuery("Round.findAll", Round.class);
		return query.getResultList();
	}
}
