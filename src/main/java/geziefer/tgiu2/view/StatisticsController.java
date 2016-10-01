package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.PropertyResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class StatisticsController implements Serializable {
	private static final long serialVersionUID = -270705952015361605L;

	@Inject
	private transient PropertyResourceBundle msg;

	@PostConstruct
	public void populateList() {
	}

}