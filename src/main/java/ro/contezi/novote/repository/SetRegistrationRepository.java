package ro.contezi.novote.repository;

import java.util.HashSet;
import java.util.Set;

import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Registration;

public class SetRegistrationRepository implements RegistrationRepository {

	private final Set<Registration> registrations = new HashSet<>();
	
	public SetRegistrationRepository() {
	}

	@Override
	public void saveRegistration(Registration registration)
			throws MultipleRegistrationException {
		if (!registrations.add(registration)) {
			throw new MultipleRegistrationException();
		};

	}

}
