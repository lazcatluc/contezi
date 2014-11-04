package ro.contezi.novote.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import ro.contezi.novote.repository.RegistrationRepository;

@ManagedBean(name = "emailValidator")
@SessionScoped
public class EmailValidator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private RegistrationRepository registrationRepository;
	
	@ManagedProperty("#{msg.duplicateEmail}")
	private String duplicateEmailMessage;
	@ManagedProperty("#{msg.incorrectEmailFormat}")
	private String incorrectEmailFormat;

	public static final String PATTERN = "(([^\\.][A-Za-z0-9._%+-]*)|(\"[^\\.][A-Za-z0-9._%+-]*\"))@(([A-Za-z0-9.-]+\\.[A-Za-z0-9.-]{2,4})|(\\[[0-9\\.]+\\]))";	


	public String getDuplicateEmailMessage() {
		return duplicateEmailMessage;
	}

	public void setDuplicateEmailMessage(String duplicateEmailMessage) {
		this.duplicateEmailMessage = duplicateEmailMessage;
	}

	public String getIncorrectEmailFormat() {
		return incorrectEmailFormat;
	}

	public void setIncorrectEmailFormat(String incorrectEmailFormat) {
		this.incorrectEmailFormat = incorrectEmailFormat;
	}

	public void emailValidator(FacesContext context, UIComponent component, Object params) {
		RegexValidator emailValidator = new RegexValidator();
		emailValidator.setPattern(EmailValidator.PATTERN);
		try {
			emailValidator.validate(context, component, params);
		}
		catch(ValidatorException ve) {
			throw new ValidatorException(new FacesMessage(getIncorrectEmailFormat()));
		}
		
		if (getRegistrationRepository().hasEmailRegistration(params.toString())) {
			throw new ValidatorException(new FacesMessage(getDuplicateEmailMessage()));
		}
	}

	public RegistrationRepository getRegistrationRepository() {
		return registrationRepository;
	}

	public void setRegistrationRepository(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}	
}
