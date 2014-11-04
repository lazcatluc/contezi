package ro.contezi.novote;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;
import ro.contezi.novote.repository.SetRegistrationRepository;

public class RegistrationsTest {
	private User user = User.SOMEONE;
	private Candidate candidate = Candidate.SOMEONE;
	private Registrations registrations;
	
	@Before
	public void setUp() {
		registrations = new Registrations();
		registrations.setCurrentCandidate(candidate);
		registrations.setCurrentUser(user);
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		SetRegistrationRepository.REGISTRATIONS.clear();
	}
	
	@Test
	public void userCanRegisterToVote() throws Exception {
		Registration registration = registrations.register(user, candidate);
		
		assertEquals(user, registration.getUser());
	}
	
	@Test
	public void candidateCanReceiveVote() throws Exception {
		Registration registration = registrations.register(user, candidate);
		
		assertEquals(candidate, registration.getCandidate());
	}

	@Test
	public void showsSuccessMessageAfterRegistration() throws Exception {
		registrations.register();
		
		assertTrue(registrations.isSuccess());
	}
	
	@Test(expected = MultipleRegistrationException.class)
	public void userCannotRegisterTwiceForACandidate() throws Exception {
		registrations.register();
		registrations.register();
	}
}
