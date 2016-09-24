package geziefer.tgiu2.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class RoundsController implements Serializable {
	private static final long serialVersionUID = -4061642452157056938L;

	@PostConstruct
	public void populateList() {
	}

}