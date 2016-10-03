package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.GameValue;

@Named
@SessionScoped
public class GamesController implements Serializable {
	private static final long serialVersionUID = -5095638556476107999L;

	@Inject
	private transient PropertyResourceBundle msg;

	private List<Game> games = new ArrayList<>();

	private String name;

	private GameValue value;

	@PostConstruct
	public void populateList() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Game> query = em.createNamedQuery("Game.findAll", Game.class);
		games = query.getResultList();
	}

	public void initFields() {
		name = "";
		value = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameValue getValue() {
		return value;
	}

	public void setValue(GameValue value) {
		this.value = value;
	}

	public List<Game> getGames() {
		return games;
	}

	public String createGame() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Game> query = em.createNamedQuery("Game.findByName", Game.class);
		query.setParameter("name", name);
		List<Game> games = query.getResultList();
		if (games.isEmpty()) {
			Game newGame = new Game();
			newGame.setName(name);
			newGame.setValue(value);
			em.getTransaction().begin();
			em.persist(newGame);
			em.getTransaction().commit();
			initFields();
			populateList();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("games.info.success"), ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getString("games.error.uniqueness"), ""));
		}

		return "";
	}
}