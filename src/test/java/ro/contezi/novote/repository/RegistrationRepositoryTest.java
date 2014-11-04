package ro.contezi.novote.repository;

import org.junit.Before;
import org.junit.Test;

import ro.contezi.novote.model.Registration;

public class RegistrationRepositoryTest {
	
	private RegistrationRepository registrationRepository;
	
	@Before
	public void setUp() {
		registrationRepository = new SetRegistrationRepository();
	}
	
	@Test
	public void hasEmailAfterAddingRegistration() throws Exception {
		registrationRepository.saveRegistration(Registration.SOMETHING);
	}
}
