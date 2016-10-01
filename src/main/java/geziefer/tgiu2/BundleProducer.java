package geziefer.tgiu2;

import java.util.PropertyResourceBundle;

import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

public class BundleProducer {

	@Produces
	public PropertyResourceBundle getBundle() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().evaluateExpressionGet(context, "#{msg}", PropertyResourceBundle.class);
	}

}