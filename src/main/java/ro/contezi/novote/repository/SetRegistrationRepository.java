package ro.contezi.novote.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;

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

		while (true) {
			Registration pair = findPairableRegistration(registration);
			if (pair != Registration.SOMETHING) {
				synchronized (pair.getUser()) {
					if (pair.getUser().hasPair()) {
						continue;
					}
					pair.getUser().setPairedVoter(registration.getUser());
					registration.getUser().setPairedVoter(pair.getUser());
				}
			}
			return;
		}
	}

	@Override
	public Registration findPairableRegistration(Registration registration) {
		Optional<Registration> pair = REGISTRATIONS.stream()
				.filter(myRegistration -> !Objects.equals(myRegistration.getCandidate(), Candidate.SOMEONE))
				.filter(myRegistration -> !Objects.equals(myRegistration.getCandidate(), registration.getCandidate()))
				.filter(myRegistration -> !myRegistration.getUser().hasPair())
				.filter(myRegistration -> Objects.equals(myRegistration.getUser().getCity(), registration.getUser().getCity()))
				.filter(myRegistration -> Objects.equals(myRegistration.getUser().getCountry(), registration.getUser().getCountry()))
					.findAny();
		if (pair.isPresent()) {
			return pair.get();
		}
		return Registration.SOMETHING;
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
