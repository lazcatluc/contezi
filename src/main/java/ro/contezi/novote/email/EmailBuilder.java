package ro.contezi.novote.email;

public interface EmailBuilder {
	EmailBuilder from(String from);
	EmailBuilder to(String to);
	EmailBuilder havingSubject(String subject);
	EmailBuilder havingBody(String body);
	
	Email build();
}
