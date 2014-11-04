package ro.contezi.novote.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Registration;

public class SetRegistrationRepository implements RegistrationRepository, Serializable {

	private static final long serialVersionUID = 2L;
	public static final Set<Registration> REGISTRATIONS = Collections.synchronizedSet(new HashSet<>());
	
	public SetRegistrationRepository() {
	}

	@Override
	public void saveRegistration(Registration registration)
			throws MultipleRegistrationException {
		if (!REGISTRATIONS.add(registration)) {
			throw new MultipleRegistrationException();
		};

	}

	@Override
	public boolean hasEmailRegistration(String email) {
		return REGISTRATIONS.stream().anyMatch(registration -> Objects.equals(registration.getUser().getEmail(), email));
	}

	@Override
	public boolean hasCnpRegistration(String cnp) {
		return REGISTRATIONS.stream().anyMatch(registration -> Objects.equals(registration.getUser().getCnp(), cnp));
	}

}
