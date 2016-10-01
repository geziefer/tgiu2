package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;

import geziefer.tgiu2.LocalEntityManagerFactory;
import geziefer.tgiu2.entity.Player;

@Named
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

	@Inject
	private transient PropertyResourceBundle msg;

	private String username = "";
	private String password = "";

	private boolean loggedIn = false;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String login() {
		boolean ok = false;
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<Player> query = em.createNamedQuery("Player.findByName", Player.class);
		query.setParameter("name", username);
		List<Player> players = query.getResultList();
		if (!players.isEmpty()) {
			Player player = players.get(0);
			if (player.getPassword().equals(DigestUtils.sha1Hex(password))) {
				ok = true;
			}
		}

		if (ok) {
			log.info("Login successful for user " + username);
			loggedIn = true;
			return "/protected/overview?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, msg.getString("login.warn.credentials"), ""));
			log.warning("Login with wrong credentials from user " + username);
			return "/login";
		}
	}

	public String logout() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		loggedIn = false;
		return "/login?faces-redirect=true";
	}

	public void checkLoginAndRedirect(ComponentSystemEvent cse) {
		FacesContext fx = FacesContext.getCurrentInstance();
		String viewId = fx.getViewRoot().getViewId();
		if (!loggedIn && !viewId.startsWith("/login")) {
			fx.getApplication().getNavigationHandler().handleNavigation(fx, null, "/login?faces-redirect=true");
		}
	}
}