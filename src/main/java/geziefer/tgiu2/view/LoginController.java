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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.primefaces.context.RequestContext;

import au.com.flyingkite.mobiledetect.UAgentInfo;
import geziefer.tgiu2.MyMessageBundle;
import geziefer.tgiu2.entity.Player;
import geziefer.tgiu2.entity.Role;

@Named
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

	@PersistenceContext
	EntityManager em;

	@Inject
	@MyMessageBundle
	private transient PropertyResourceBundle msg;

	private String username = "";

	private String password = "";

	private String newPassword = "";

	private Player player = null;

	private boolean mobile = false;

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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean isMobile() {
		return mobile;
	}

	public String login() {
		boolean ok = false;
		TypedQuery<Player> query = em.createNamedQuery("Player.findByName", Player.class);
		query.setParameter("name", username);
		List<Player> players = query.getResultList();
		if (!players.isEmpty()) {
			Player player = players.get(0);
			if (player.getPassword().equals(DigestUtils.sha1Hex(password))) {
				ok = true;
				this.player = player;
			}
		}

		if (ok) {
			log.info("Login successful for user " + username);
			loggedIn = true;
			if (mobile) {
				return "/mobile/overview?faces-redirect=true";
			} else {
				return "/desktop/overview?faces-redirect=true";
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, msg.getString("login.warn.credentials"), ""));
			log.warning("Login with wrong credentials from user " + username);
			if (mobile) {
				return "/loginMobile";
			} else {
				return "/loginDesktop";
			}
		}
	}

	public String logout() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		log.info("Logout successful for user " + username);
		loggedIn = false;
		if (mobile) {
			return "/loginMobile?faces-redirect=true";
		} else {
			return "/loginDesktop?faces-redirect=true";
		}
	}

	public boolean hasRole(Role role) {
		return player == null ? false : player.hasRole(role);
	}
	
	public void checkLoginAndRedirect(ComponentSystemEvent cse) {
		checkMobile();

		FacesContext fx = FacesContext.getCurrentInstance();
		String viewId = fx.getViewRoot().getViewId();
		if (!loggedIn && !viewId.startsWith("/login")) {
			redirectLogin();
		}
	}

	public void checkDeviceAndRedirect(ComponentSystemEvent cse) {
		checkMobile();
		redirectLogin();
	}

	public String changePassword() {
		TypedQuery<Player> query = em.createNamedQuery("Player.findByName", Player.class);
		query.setParameter("name", username);
		List<Player> players = query.getResultList();
		if (!players.isEmpty()) {
			Player player = players.get(0);
			player.setPassword(DigestUtils.sha1Hex(newPassword));
			em.merge(player);
			RequestContext.getCurrentInstance().execute("PF('passwordDialog').hide()");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, msg.getString("login.password.success"), ""));
		}

		return "";
	}

	private void checkMobile() {
		String userAgent = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest())
				.getHeader("user-agent");
		UAgentInfo agentInfo = new UAgentInfo(userAgent, null);
		mobile = agentInfo.detectMobileQuick() || agentInfo.detectTierTablet();
	}

	private void redirectLogin() {
		FacesContext fx = FacesContext.getCurrentInstance();
		if (mobile) {
			fx.getApplication().getNavigationHandler().handleNavigation(fx, null, "/loginMobile?faces-redirect=true");
		} else {
			fx.getApplication().getNavigationHandler().handleNavigation(fx, null, "/loginDesktop?faces-redirect=true");
		}
	}

}