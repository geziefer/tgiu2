package geziefer.tgiu2;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.omnifaces.cdi.Startup;

import geziefer.tgiu2.entity.Config;

@Startup
@Named
public class ApplicationConfig {
	private String doodleURL;

	@PostConstruct
	public void populateConfig() {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		Config config = em.find(Config.class, "doodleURL");
		if (config == null) {
			setDoodleURL("http://doodle.com");
		} else {
			this.doodleURL = config.getValue();
		}
	}

	public String getDoodleURL() {
		return doodleURL;
	}

	public void setDoodleURL(String doodleURL) {
		EntityManager em = LocalEntityManagerFactory.createEntityManager();
		Config config = new Config();
		config.setName("doodleURL");
		config.setValue(doodleURL);
		em.getTransaction().begin();
		em.merge(config);
		em.getTransaction().commit();
		this.doodleURL = doodleURL;
	}
}
