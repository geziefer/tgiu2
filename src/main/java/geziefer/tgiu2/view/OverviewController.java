package geziefer.tgiu2.view;

import java.io.Serializable;
import java.util.PropertyResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class OverviewController implements Serializable {
	private static final long serialVersionUID = 6382467439487851769L;

	@Inject
	private transient PropertyResourceBundle msg;

	@PostConstruct
	public void populateList() {
	}

}