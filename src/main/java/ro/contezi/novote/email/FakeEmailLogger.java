package ro.contezi.novote.email;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;

public class FakeEmailLogger implements Emailer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(FakeEmailLogger.class.getCanonicalName());
	
	@Inject
	private EmailBuilder emailBuilder;

	@Override
	public void sendEmail(Email any) {
		LOGGER.info("Email \n From: "+any.getFrom()+"\nTo: "+any.getTo()+"\nSubject: "+any.getSubject()+"\n\n"+any.getBody());
	}

	@Override
	public EmailBuilder getEmailBuilder() {
		if (emailBuilder == null) {
			emailBuilder = new SimpleEmailBuilder();
		}
		return emailBuilder;
	}

}
