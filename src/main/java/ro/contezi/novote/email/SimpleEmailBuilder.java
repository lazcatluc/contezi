package ro.contezi.novote.email;

import java.io.Serializable;

public class SimpleEmailBuilder implements EmailBuilder, Serializable {

	private static final long serialVersionUID = 1L;
	
	private String from = "sender@example.com";
	private String to = "receiver@example.com";
	private String subject = "Subject";
	private String body = "Body";
	
	@Override
	public EmailBuilder from(String from) {
		this.from = from;
		return this;
	}

	@Override
	public EmailBuilder to(String to) {
		this.to = to;
		return this;
	}

	@Override
	public EmailBuilder havingSubject(String subject) {
		this.subject = subject;
		return this;
	}

	@Override
	public EmailBuilder havingBody(String body) {
		this.body = body;
		return this;
	}

	@Override
	public Email build() {
		return new Email(from, to, subject, body);
	}

}
