package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.List;

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

	public String queryUsers() {
			EntityManager em = LocalEntityManagerFactory.createEntityManager();
			TypedQuery<User> tq = em.createNamedQuery("User.findAll", User.class);
			List<User> users = tq.getResultList();
			for (User user : users) {
				System.out.println(user.getUsername());
			}
			return "";
	}
}