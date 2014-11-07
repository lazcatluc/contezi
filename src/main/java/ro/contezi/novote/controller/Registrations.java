package ro.contezi.novote.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.Voter;
import ro.contezi.novote.repository.RegistrationRepository;

@ManagedBean(name = "registrations")
@SessionScoped
public class Registrations implements Serializable {

	private static final long serialVersionUID = 5L;
	
	@Inject
	@Config
	private RegistrationRepository registrationRepository;
	
	private Voter currentUser = new Voter();
	private Candidate currentCandidate = new Candidate();
	private boolean success;
	
	public Registration register(Voter user, Candidate candidate) {
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

	public RegistrationRepository getRegistrationRepository() {
		return registrationRepository;
	}

	public void setRegistrationRepository(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

	public Voter getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(Voter currentUser) {
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
