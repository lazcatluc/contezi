package ro.contezi.novote.email;

public interface Emailer {

	void sendEmail(Email any);
	EmailBuilder getEmailBuilder();

}
