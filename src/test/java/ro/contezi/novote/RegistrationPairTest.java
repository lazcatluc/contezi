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
	
	@Before
	public void setUp() {
		SetRegistrationRepository.REGISTRATIONS.clear();
	}
	
	private Registrations registrations() {
		Registrations registrations = new Registrations();
		registrations.setRegistrationRepository(new SetRegistrationRepository());
		return registrations;
	}
	
	@Test
	public void canFindPairForTwoAfterAddingOne() throws Exception {
		User one = new User();
		one.setEmail("one@one.com");
		one.setCnp("1");
		Candidate oneCandidate = new Candidate();
		oneCandidate.setName("One");
		Registration first = registrations().register(one, oneCandidate);
		
		User two = new User();
		two.setEmail("two@two.com");
		one.setCnp("2");
		Candidate twoCandidate = new Candidate();
		twoCandidate.setName("Two");
		Registration second = new Registration();
		second.setUser(two);
		second.setCandidate(twoCandidate);
		
		assertEquals(first, registrations().getRegistrationRepository().findPairableRegistration(second));
		
	}
	
	@Test
	public void canPairRegistrationsToDifferentCandidatesInTheSameCity() throws Exception {
		User one = new User();
		one.setEmail("one@one.com");
		one.setCnp("1");
		Candidate oneCandidate = new Candidate();
		oneCandidate.setName("One");
		registrations().register(one, oneCandidate);
		
		User two = new User();
		two.setEmail("two@two.com");
		one.setCnp("2");
		Candidate twoCandidate = new Candidate();
		twoCandidate.setName("Two");
		registrations().register(two, twoCandidate);
		
		
		assertEquals(two, one.getPairedVoter());
		assertEquals(one, two.getPairedVoter());
	}
}
