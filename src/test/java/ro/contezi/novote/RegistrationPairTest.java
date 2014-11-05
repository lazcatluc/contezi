package ro.contezi.novote;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;
import ro.contezi.novote.repository.SetRegistrationRepository;

public class RegistrationPairTest {
	private User one;
	private User two;
	private Candidate oneCandidate;
	private Candidate twoCandidate;
	
	@Before
	public void setUp() {
		SetRegistrationRepository.REGISTRATIONS.clear();
		one = new User();
		one.setEmail("one@one.com");
		one.setCnp("1");
		two = new User();
		two.setEmail("two@two.com");
		one.setCnp("2");
		oneCandidate = new Candidate();
		oneCandidate.setName("One");
		twoCandidate = new Candidate();
		twoCandidate.setName("Two");
	}
	
	private Registrations registrations() {
		Registrations registrations = new Registrations();
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		return registrations;
	}
	
	@Test
	public void canFindPairForTwoAfterAddingOne() throws Exception {
		Registration first = registrations().register(one, oneCandidate);
		Registration second = new Registration();
		second.setUser(two);
		second.setCandidate(twoCandidate);
		
		assertEquals(first, registrations().getRegistrationRepository().findPairableRegistration(second));
		
	}
	
	@Test
	public void canPairRegistrationsToDifferentCandidatesInTheSameCity() throws Exception {
		registrations().register(one, oneCandidate);
		registrations().register(two, twoCandidate);
		
		assertEquals(two, one.getPairedVoter());
		assertEquals(one, two.getPairedVoter());
	}
}
