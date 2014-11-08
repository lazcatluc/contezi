package ro.contezi.novote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.controller.Registrations;
import ro.contezi.novote.email.Email;
import ro.contezi.novote.email.EmailBuilder;
import ro.contezi.novote.email.Emailer;
import ro.contezi.novote.email.SimpleEmailBuilder;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.Voter;
import ro.contezi.novote.repository.CandidatesRepository;
import ro.contezi.novote.repository.SetRegistrationRepository;

public class RegistrationPairTest {
	private Voter one;
	private Voter two;
	private Candidate oneCandidate;
	private Candidate twoCandidate;
	private Emailer emailer;
	private EmailBuilder emailBuilder;
	
	@Before
	public void setUp() {
		SetRegistrationRepository.REGISTRATIONS.clear();
		one = new Voter();
		one.setPairedVoter(Voter.SOMEONE);
		one.setEmail("one@one.com");
		one.setCnp("1");
		two = new Voter();
		two.setPairedVoter(Voter.SOMEONE);
		two.setEmail("two@two.com");
		one.setCnp("2");
		oneCandidate = new Candidate();
		oneCandidate.setName("One");
		twoCandidate = new Candidate();
		twoCandidate.setName("Two");
		emailer = mock(Emailer.class);
		emailBuilder = new SimpleEmailBuilder();
		when(emailer.getEmailBuilder()).thenReturn(emailBuilder);
	}
	
	private Registrations registrations() {
		Registrations registrations = new Registrations();
		SetRegistrationRepository registrationRepository = new SetRegistrationRepository();
		registrationRepository.setPairHasBeenFormedBody("{0} {1} {2} {3}");
		registrations.setRegistrationRepository(registrationRepository);
		registrationRepository.setEmailer(emailer);
		CandidatesRepository candidatesRepository = mock(CandidatesRepository.class);
		when(candidatesRepository.getAllCandidates()).thenReturn(Arrays.asList(oneCandidate, twoCandidate));
		registrations.setCandidatesRepository(candidatesRepository);

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
	public void firstDoesntHavePairAfterAddingOne() throws Exception {
		Registration first = registrations().register(one, oneCandidate);
		
		assertFalse(first.getUser().hasPair());
	}
	
	@Test
	public void canPairRegistrationsOfFirstUserToDifferentCandidatesInTheSameCityInTheSameCountry() throws Exception {
		addRegistrations();
		
		assertEquals(two, one.getPairedVoter());
	}
	
	@Test
	public void canPairRegistrationsOfSecondUserToDifferentCandidatesInTheSameCityInTheSameCountry() throws Exception {
		addRegistrations();
		
		assertEquals(one, two.getPairedVoter());
	}
	
	@Test
	public void cannotPairIfDifferentCountry() throws Exception {
		one.setCountry("Romania");
		two.setCountry("Bulgaria");
		
		addRegistrations();
		
		assertFalse(one.hasPair());
	}
	
	@Test
	public void cannotPairIfDifferentCity() throws Exception {
		one.setCity("Bucuresti");
		two.setCity("Ploiesti");
		
		addRegistrations();
		
		assertFalse(one.hasPair());
	}
	
	@Test
	public void afterPairingEmailsAreSentToBoth() throws Exception {
		addRegistrations();
		
		verify(emailer, times(2)).sendEmail(any(Email.class));
	}

	protected void addRegistrations() {
		registrations().register(one, oneCandidate);
		registrations().register(two, twoCandidate);
	}
}
