package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private static Logger log = Logger.getLogger(LoginController.class.getName());

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
		if (username.equals("alex") && password.equals("alex")) {
			log.info("Login successful for user " + username);
			loggedIn = true;
			return "/protected/overview?faces-redirect=true";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Falscher Username / Passwort", ""));
			log.warning("Login with wrong credentials from user " + username);
			return "/login";
		}
	}

	public String logout() {
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		loggedIn = false;
		return "/login?faces-redirect=true";
	}
}