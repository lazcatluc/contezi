package ro.contezi.novote.email;

import java.io.Serializable;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ro.contezi.novote.config.Config;

@Config
public class GmailSender implements Emailer, Serializable {

	private static final long serialVersionUID = 1L;
	private static final Properties GMAIL_PROPERTIES = new Properties();
	static {
		GMAIL_PROPERTIES.put("mail.smtp.auth", "true");
		GMAIL_PROPERTIES.put("mail.smtp.starttls.enable", "true");
		GMAIL_PROPERTIES.put("mail.smtp.host", "smtp.gmail.com");
		GMAIL_PROPERTIES.put("mail.smtp.port", "587");
        
        // Imports: javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
                }
                @Override
                public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance( "SSL" );
            sslContext.init( null, trustAllCerts, new java.security.SecureRandom() );
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            GMAIL_PROPERTIES.put("mail.smtp.ssl.socketFactory", sslSocketFactory);

        } catch ( final Exception e ) {
            throw new ExceptionInInitializerError(e);
        }
	}
	
	@Inject
	private EmailBuilder emailBuilder;
	@Inject @Config
	private String username;
	@Inject @Config
	private String password;
	@Inject @Config
	private String realFrom;

	@Override
	public void sendEmail(Email email) {
		Session session = newSession();
 
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(getRealFrom()));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email.getTo()));
			message.setReplyTo(InternetAddress.parse(email.getFrom()));
			message.setSubject(email.getSubject());
			message.setText(email.getBody());

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	protected Session newSession() {
		return Session.getInstance(GMAIL_PROPERTIES,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(getUsername(), getPassword());
			}
		  });
	}

	@Override
	public EmailBuilder getEmailBuilder() {
		return emailBuilder;
	}
	
	public void setEmailBulder(EmailBuilder emailBuilder) {
		this.emailBuilder = emailBuilder;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealFrom() {
		return realFrom;
	}

	public void setRealFrom(String realFrom) {
		this.realFrom = realFrom;
	}

}
