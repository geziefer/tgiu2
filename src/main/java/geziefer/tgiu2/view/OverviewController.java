package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import geziefer.tgiu2.entity.User;
import geziefer.tgiu2.listener.LocalEntityManagerFactory;

@ManagedBean
@SessionScoped
public class OverviewController implements Serializable {

	private static final long serialVersionUID = 3893948528151679341L;

	private List<User> users = new ArrayList<>();

	@PostConstruct
	public void populateUserlist() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		TypedQuery<User> tq = em.createNamedQuery("User.findAll", User.class);
		users = tq.getResultList();
	}

	public List<User> getUsers() {
		return users;
	}
}