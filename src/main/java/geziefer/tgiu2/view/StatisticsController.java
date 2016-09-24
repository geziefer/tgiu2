package geziefer.tgiu2.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class StatisticsController implements Serializable {
	private static final long serialVersionUID = -270705952015361605L;

	@PostConstruct
	public void populateList() {
	}

}