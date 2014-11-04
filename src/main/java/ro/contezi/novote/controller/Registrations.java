package ro.contezi.novote.controller;

import java.io.Serializable;

import javax.inject.Inject;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.RegexValidator;

import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;
import ro.contezi.novote.repository.RegistrationRepository;

@ManagedBean(name = "registrations")
@SessionScoped
public class Registrations implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
			.getLogger(Candidates.class.getName());
	public static final String EMAIL_PATTERN = "(([^\\.][A-Za-z0-9._%+-]*)|(\"[^\\.][A-Za-z0-9._%+-]*\"))@(([A-Za-z0-9.-]+\\.[A-Za-z0-9.-]{2,4})|(\\[[0-9\\.]+\\]))";		
	
	@Inject
	private RegistrationRepository registrationRepository;
	
	private User currentUser = new User();
	
	private Candidate currentCandidate = Candidate.SOMEONE;
	private boolean success;

	public Registration register(User user, Candidate candidate) {
		Registration registration = new Registration();
		registration.setUser(user);
		registration.setCandidate(candidate);
		
		registrationRepository.saveRegistration(registration);
		success = true;
		return registration;
	}
	
	public void register() {
		register(currentUser, currentCandidate);
	}
	
	public void emailValidator(FacesContext context, UIComponent component, Object params) {
		RegexValidator emailValidator = new RegexValidator();
		emailValidator.setPattern(EMAIL_PATTERN);
		emailValidator.validate(context, component, params);
	}
	
	public void cnpValidator(FacesContext context, UIComponent component, Object params) {
		LOGGER.info("Validating: "+context+" - "+component+" - "+params);
	}

	public RegistrationRepository getRegistrationRepository() {
		return registrationRepository;
	}

	public void setRegistrationRepository(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public Candidate getCurrentCandidate() {
		return currentCandidate;
	}

	public void setCurrentCandidate(Candidate currentCandidate) {
		this.currentCandidate = currentCandidate;
	}

	public boolean isSuccess() {
		return success;
	}

}
