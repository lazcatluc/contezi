package ro.contezi.novote.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;
import ro.contezi.novote.repository.RegistrationRepository;

@Named("registrations")
@SessionScoped
public class Registrations implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private RegistrationRepository registrationRepository;

	public Registration register(User user, Candidate candidate) {
		Registration registration = new Registration();
		registration.setUser(user);
		registration.setCandidate(candidate);
		
		registrationRepository.saveRegistration(registration);
		
		return registration;
	}

	public RegistrationRepository getRegistrationRepository() {
		return registrationRepository;
	}

	public void setRegistrationRepository(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

}
