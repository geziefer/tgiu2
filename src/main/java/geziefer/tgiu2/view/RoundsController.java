package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.primefaces.model.DualListModel;

import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@Named
@SessionScoped
public class RoundsController implements Serializable {
	private static final long serialVersionUID = -4061642452157056938L;

	private List<String> players = new ArrayList<>();
	private List<String> selectedPlayers = new ArrayList<>();
	private DualListModel<String> gameModel;

	@PostConstruct
	public void populateList() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
		List<Player> players = query.getResultList();
		for (Player player : players) {
			this.players.add(player.getName());
		}
	}

	public void initFields() {
		gameModel = new DualListModel<>(players, selectedPlayers);
	}

	public DualListModel<String> getGameModel() {
		return gameModel;
	}

	public void setGameModel(DualListModel<String> gameModel) {
		this.gameModel = gameModel;
	}

	public String createRound() {
		selectedPlayers = gameModel.getTarget();
		for (String name : selectedPlayers) {
			System.out.println(name);
		}

		return "";
	}
}