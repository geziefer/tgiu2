package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PropertyResourceBundle;

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

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

import geziefer.tgiu2.MyMessageBundle;
import geziefer.tgiu2.entity.Comment;
import geziefer.tgiu2.entity.Game;
import geziefer.tgiu2.entity.GameValue;
import geziefer.tgiu2.entity.Player;

@Named
@SessionScoped
@Transactional(value = TxType.REQUIRED)
public class GamesController implements Serializable {
	private static final long serialVersionUID = -5095638556476107999L;

	@PersistenceContext
	EntityManager em;

	@Inject
	@MyMessageBundle
	private transient PropertyResourceBundle msg;

	@Inject
	private LoginController loginController;

	private List<Game> games = new ArrayList<>();

	private String name;

	private GameValue value;

	private Comment comment;

	private String commentText;

	private String formattedComments;

	private String searchGame;

	public void initFields() {
		TypedQuery<Game> query = em.createNamedQuery("Game.findAllEagerly", Game.class);
		games = query.getResultList();
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

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public List<Game> getGames() {
		return games;
	}

	public String getFormattedComments() {
		return formattedComments;
	}

	public String getSearchGame() {
		return searchGame;
	}

	public void setSearchGame(String searchGame) {
		this.searchGame = searchGame;
	}

	public String createGame() {
		TypedQuery<Game> query = em.createNamedQuery("Game.findByName", Game.class);
		query.setParameter("name", name);
		List<Game> games = query.getResultList();
		if (games.isEmpty()) {
			Game newGame = new Game();
			newGame.setName(name);
			newGame.setValue(value);
			newGame.setComments(new ArrayList<>());
			em.persist(newGame);
			initFields();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("games.info.success"), ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getString("games.error.uniqueness"), ""));
		}

		return "";
	}

	
	public void editName(CellEditEvent event) {
		String oldValue = (String) event.getOldValue();
		String newValue = (String) event.getNewValue();
		Game game = games.stream().filter(g -> g.getName().equals(newValue)).findFirst().get();

		try {
			em.merge(game);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("games.info.success"), ""));
		} catch (Exception e) {
			game.setName(oldValue);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, msg.getString("games.error.uniqueness"), ""));
		}
	}

	public void selectComment(Integer row) {
		String lineBreak = "<br/>";
		Game game = games.get(row);
		StringBuilder comments = new StringBuilder();
		for (Comment comment : game.getComments()) {
			comments.append(comment.getPlayer().getName()).append(": ").append(comment.getComment()).append(lineBreak);
		}
		formattedComments = comments.toString();
		Player player = loginController.getPlayer();
		Optional<Comment> commentPlayer = game.getComments().stream()
				.filter(g -> g.getPlayer().getName().equals(player.getName())).findFirst();
		comment = commentPlayer.orElse(new Comment(game, player, ""));
		commentText = comment.getComment();
		RequestContext.getCurrentInstance().execute("PF('commentDialog').show()");
	}

	public String storeComment() {
		comment.setComment(commentText);
		em.merge(comment);
		initFields();
		RequestContext.getCurrentInstance().execute("PF('commentDialog').hide()");
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("games.comment.success"), ""));

		return "";
	}
}