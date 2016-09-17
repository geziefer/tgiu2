package lab.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MainController implements Serializable {

	private static final long serialVersionUID = 3973801993975443027L;

	private String name;
	private String primeName;
	private String richName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimeName() {
		return primeName;
	}

	public void setPrimeName(String primeName) {
		this.primeName = primeName;
	}

	public String getRichName() {
		return richName;
	}

	public void setRichName(String richName) {
		this.richName = richName;
	}
}