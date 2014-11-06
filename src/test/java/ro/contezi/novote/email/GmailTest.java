package ro.contezi.novote.email;

import com.sun.mail.smtp.SMTPTransport;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.logging.Level;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class GmailTest {
	public static void main(String[] args) {
		
		final String username = "lazcatluc@gmail.com";
		final String password = System.getProperty("gmail.password");
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
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
            props.put("mail.smtp.ssl.socketFactory", sslSocketFactory);

        } catch ( final Exception e ) {
            e.printStackTrace();
        }
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("lazcatluc@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("lazcatluc@gmail.com"));
			message.setReplyTo(InternetAddress.parse("lazcatluc@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
			System.out.println("Sending...");
			//((SMTPTransport)Transport).set
                    Transport.send(message);
 
			System.out.println("...sent.");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
