package geziefer.tgiu2.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class OverviewController implements Serializable {
	private static final long serialVersionUID = 6382467439487851769L;

	@PostConstruct
	public void populateList() {
	}

}