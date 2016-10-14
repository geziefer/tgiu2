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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.GameValue;

@Named
@SessionScoped
@Transactional(value=TxType.REQUIRED)
public class GamesController implements Serializable {
	private static final long serialVersionUID = -5095638556476107999L;

	@PersistenceContext
	EntityManager em;
	
	@Inject
	private transient PropertyResourceBundle msg;

	private List<Game> games = new ArrayList<>();

	private String name;

	private GameValue value;

	@PostConstruct
	public void populateList() {
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
		TypedQuery<Game> query = em.createNamedQuery("Game.findByName", Game.class);
		query.setParameter("name", name);
		List<Game> games = query.getResultList();
		if (games.isEmpty()) {
			Game newGame = new Game();
			newGame.setName(name);
			newGame.setValue(value);
			em.persist(newGame);
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