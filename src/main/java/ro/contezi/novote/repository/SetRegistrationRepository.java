package ro.contezi.novote.repository;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.email.Email;
import ro.contezi.novote.email.EmailBuilder;
import ro.contezi.novote.email.Emailer;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;

public class SetRegistrationRepository implements RegistrationRepository, Serializable {

	private static final long serialVersionUID = 3L;
	public static final Set<Registration> REGISTRATIONS = Collections.synchronizedSet(new HashSet<>());
	
	@Inject @Config
	private Emailer emailer;
	
	@Inject @Config
	private String pairHasBeenFormed;
	
	@Inject @Config
	private String pairHasBeenFormedBody;
	
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
					EmailBuilder emailBuilder = getEmailer().getEmailBuilder()
							.havingSubject(pairHasBeenFormed)
							.havingBody(MessageFormat.format(pairHasBeenFormedBody, 
									registration.getUser().getName(), registration.getUser().getEmail(),
									pair.getUser().getName(), pair.getUser().getEmail()));
					Email firstToSecond = emailBuilder
							.from(registration.getUser().getEmail())
							.to(pair.getUser().getEmail()).build();
					Email secondToFirst = emailBuilder
							.from(pair.getUser().getEmail())
							.to(registration.getUser().getEmail()).build();
					getEmailer().sendEmail(firstToSecond);
					getEmailer().sendEmail(secondToFirst);
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

	@Override
	public Emailer getEmailer() {
		return emailer;
	}

	@Override
	public void setEmailer(Emailer emailer) {
		this.emailer = emailer;
	}

	public String getPairHasBeenFormed() {
		return pairHasBeenFormed;
	}

	public void setPairHasBeenFormed(String pairHasBeenFormed) {
		this.pairHasBeenFormed = pairHasBeenFormed;
	}

	public String getPairHasBeenFormedBody() {
		return pairHasBeenFormedBody;
	}

	public void setPairHasBeenFormedBody(String pairHasBeenFormedBody) {
		this.pairHasBeenFormedBody = pairHasBeenFormedBody;
	}

}
