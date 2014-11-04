package ro.contezi.novote;

import static org.junit.Assert.*;

import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;

public class RegistrationTest {
	@Test
	public void userCanRegisterToVoteForACandidate() throws Exception {
		User user = User.SOMEONE;
		Candidate candidate = Candidate.SOMEONE;
		Registrations registrations = new Registrations();
		
		Registration registration = registrations.register(user, candidate);
		
		assertEquals(candidate, registration.getCandidate());
		assertEquals(user, registration.getUser());
	}

}
