package ro.contezi.novote.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;

@Named("registrations")
@SessionScoped
public class Registrations implements Serializable {

	private static final long serialVersionUID = 1L;

	public Registration register(User user, Candidate candidate) {
		Registration registration = new Registration();
		registration.setUser(user);
		registration.setCandidate(candidate);
		
		return registration;
	}

}
