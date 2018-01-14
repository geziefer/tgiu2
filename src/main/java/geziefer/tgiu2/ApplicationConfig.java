package geziefer.tgiu2;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.omnifaces.cdi.Startup;

import geziefer.tgiu2.entity.Config;

@Startup
@Named
@Transactional(value = TxType.REQUIRED)
public class ApplicationConfig {
	private String doodleURL;

	@PersistenceContext
	EntityManager em;

	@PostConstruct
	public void populateConfig() {
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
		Config config = new Config();
		config.setName("doodleURL");
		config.setValue(doodleURL);
		em.merge(config);
		this.doodleURL = doodleURL;
	}
}
