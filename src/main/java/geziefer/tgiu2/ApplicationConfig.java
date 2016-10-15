package geziefer.tgiu2;

import javax.inject.Named;

import org.omnifaces.cdi.Startup;

@Startup
@Named
public class ApplicationConfig {
	private String doodleURL = "http://doodle.com";

	public String getDoodleURL() {
		return doodleURL;
	}

	public void setDoodleURL(String doodleURL) {
		this.doodleURL = doodleURL;
	}
}
