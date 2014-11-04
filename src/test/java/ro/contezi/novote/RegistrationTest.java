package ro.contezi.novote;

import static org.junit.Assert.*;

import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;
import ro.contezi.novote.repository.SetRegistrationRepository;

public class RegistrationTest {
	@Test
	public void userCanRegisterToVoteForACandidate() throws Exception {
		User user = User.SOMEONE;
		Candidate candidate = Candidate.SOMEONE;
		Registrations registrations = new Registrations();
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		
		Registration registration = registrations.register(user, candidate);
		
		assertEquals(candidate, registration.getCandidate());
		assertEquals(user, registration.getUser());
	}

	@Test(expected = MultipleRegistrationException.class)
	public void userCannotRegisterTwiceForACandidate() throws Exception {
		User user = User.SOMEONE;
		Candidate candidate = Candidate.SOMEONE;
		Registrations registrations = new Registrations();
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		
		registrations.register(user, candidate);
		registrations.register(user, candidate);
	}
}
