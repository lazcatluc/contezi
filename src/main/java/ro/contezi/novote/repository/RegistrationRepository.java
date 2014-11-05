package ro.contezi.novote.repository;

import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Registration;

public interface RegistrationRepository {
	void saveRegistration(Registration registration) throws MultipleRegistrationException;

	boolean hasEmailRegistration(String email);

	boolean hasCnpRegistration(String cnp);

	Registration findPairableRegistration(Registration registration);
}
