package ro.contezi.novote;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.exception.MultipleRegistrationException;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.Voter;
import ro.contezi.novote.repository.CandidatesRepository;
import ro.contezi.novote.repository.SetRegistrationRepository;

public class RegistrationsTest {
	private Voter user = Voter.SOMEONE;
	private Candidate candidate;
	private CandidatesRepository candidatesRepository;
	private Registrations registrations;
	
	@Before
	public void setUp() {
		registrations = new Registrations();
		candidate = new Candidate("candidate");
		registrations.setCurrentCandidate(candidate);
		registrations.setCurrentUser(user);
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		candidatesRepository = mock(CandidatesRepository.class);
		when(candidatesRepository.getAllCandidates()).thenReturn(Collections.singletonList(candidate));
		registrations.setCandidatesRepository(candidatesRepository);
		
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
	
	@Test
	public void doesNotRegisterNonExistentCandidate() throws Exception {
		registrations.register(user, new Candidate("Somebody Else"));
		
		assertFalse(registrations.isSuccess());
	}
	
	@Test
	public void remembersNonExistentCandidate() throws Exception {
		registrations.register(user, new Candidate("Somebody Else"));
		
		assertTrue(registrations.isNonExistentCandidate());
	}
	
	@Test
	public void votingAfterChoosingNonExistentCandidateForgetsNonExistentCandidate() throws Exception {
		registrations.register(user, new Candidate("Somebody Else"));
		registrations.register();
		
		assertFalse(registrations.isNonExistentCandidate());
	}
	
	@Test(expected = MultipleRegistrationException.class)
	public void userCannotRegisterTwiceForACandidate() throws Exception {
		registrations.register();
		registrations.register();
	}
}
