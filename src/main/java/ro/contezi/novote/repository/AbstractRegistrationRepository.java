package ro.contezi.novote.repository;

import java.text.MessageFormat;

import javax.inject.Inject;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.email.Email;
import ro.contezi.novote.email.EmailBuilder;
import ro.contezi.novote.email.Emailer;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Registration;

public abstract class AbstractRegistrationRepository implements RegistrationRepository {

	@Inject @Config
	private Emailer emailer;	

	@Inject @Config
	private String pairHasBeenFormed;
	
	@Inject @Config
	private String pairHasBeenFormedBody;

	protected abstract void persistRegistration(Registration registration);
	
	@Override
	public final void saveRegistration(Registration registration)
			throws MultipleRegistrationException {
		persistRegistration(registration);
		
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
							.havingSubject(getPairHasBeenFormed())
							.havingBody(MessageFormat.format(getPairHasBeenFormedBody(), 
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
