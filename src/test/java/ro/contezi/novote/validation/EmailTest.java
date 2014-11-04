package ro.contezi.novote.validation;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ro.contezi.novote.controller.Registrations;

@RunWith(Parameterized.class)
public class EmailTest {
	private final boolean valid;
	private final String email;
		
	@Test
	public void validatesEmail() throws Exception {
		assertEquals(valid, validate(Registrations.EMAIL_PATTERN, email));
	}
	
	public boolean validate(String regex, String email) {
		Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
	}
	
	public EmailTest(boolean valid, String email) {
		this.valid = valid;
		this.email = email;
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(
				invalid(""),
				invalid("a"),
				invalid("a@b"),
				invalid("a.a@b"),
				invalid("email@domain@domain.com"),
				invalid(".email@domain.com"),
				invalid("あいうえお@domain.com"),
				invalid("email@domain.com (Joe Smith)"),
				
				valid("example@example.com"),
				valid("a@a.aa"),
				valid("a.a@a.a.aa"), 
				valid("email@123.123.123.123"),
				valid("email@[123.123.123.123]"),
				valid("\"email\"@domain.com"),
				valid("firstname+lastname@domain.com")
			);
	}
	
	public static Object[] valid(String email) {
		return new Object[] {true, email};
	}
	
	public static Object[] invalid(String email) {
		return new Object[] {false, email};
	}
}
