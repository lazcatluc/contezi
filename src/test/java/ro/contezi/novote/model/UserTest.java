package ro.contezi.novote.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	@Test
	public void newUserDoesntHavePair() throws Exception {
		Voter user = new Voter();
		user.setPairedVoter(Voter.SOMEONE);
		
		assertFalse(user.hasPair());
	}
}
