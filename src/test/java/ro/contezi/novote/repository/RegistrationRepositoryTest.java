package ro.contezi.novote.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.model.Registration;
import ro.contezi.novote.model.User;

public class RegistrationRepositoryTest {
	
	private RegistrationRepository registrationRepository;
	
	@Before
	public void setUp() {
		registrationRepository = new SetRegistrationRepository();
		SetRegistrationRepository.REGISTRATIONS.clear();
	}
	
	@Test
	public void hasEmailAfterAddingRegistration() throws Exception {
		registrationRepository.saveRegistration(Registration.SOMETHING);
		
		assertTrue(registrationRepository.hasEmailRegistration(User.SOMEONE.getEmail()));
	}
	
	@Test
	public void hasCnpAfterAddingRegistration() throws Exception {
		registrationRepository.saveRegistration(Registration.SOMETHING);
		
		assertTrue(registrationRepository.hasCnpRegistration(User.SOMEONE.getCnp()));
	}
}
