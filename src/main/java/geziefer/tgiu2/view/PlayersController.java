package geziefer.tgiu2.view;

import java.io.Serializable;
import java.text.MessageFormat;
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

import org.apache.commons.codec.digest.DigestUtils;

import geziefer.tgiu2.MyMessageBundle;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Role;

@Named
@SessionScoped
@Transactional(value=TxType.REQUIRED)
public class PlayersController implements Serializable {
	private static final long serialVersionUID = 2694864640991849406L;

	@PersistenceContext
	EntityManager em;
	
	@Inject
	@MyMessageBundle
	private transient PropertyResourceBundle msg;

	private List<Player> players = new ArrayList<>();

	private String name;

	private String password;

	@PostConstruct
	public void populateList() {
		TypedQuery<Player> query = em.createNamedQuery("Player.findAll", Player.class);
		players = query.getResultList();
	}

	public void initFields() {
		name = "";
		password = "";
	}

	public List<Player> getPlayers() {
		return players;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String createPlayer() {
		TypedQuery<Player> query = em.createNamedQuery("Player.findByName", Player.class);
		query.setParameter("name", name);
		List<Player> players = query.getResultList();
		if (players.isEmpty()) {
			Player newPlayer = new Player();
			newPlayer.setName(name);
			newPlayer.setPassword(DigestUtils.sha1Hex(password));
			newPlayer.setRole(Role.USER);
			em.persist(newPlayer);
			initFields();
			populateList();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("players.info.success"), ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getString("players.error.uniqueness"), ""));
		}

		return "";
	}

	public String resetPassword(Integer row) {
		String name = players.get(row).getName();
		TypedQuery<Player> query = em.createNamedQuery("Player.findByName", Player.class);
		query.setParameter("name", name);
		List<Player> players = query.getResultList();
		if (!players.isEmpty()) {
			Player player = players.get(0);
			player.setPassword(DigestUtils.sha1Hex(name));
			em.merge(player);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					MessageFormat.format(msg.getString("players.reset.success"), name), ""));
		}

		return "";
	}

}