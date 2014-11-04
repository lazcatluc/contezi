package ro.contezi.novote;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import ro.contezi.novote.controller.Candidates;
import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.repository.CandidatesArray;
import ro.contezi.novote.repository.CandidatesRepository;

public class CandidatesTest {

	@Test
	public void userCanViewTheCandidates() throws Exception {
		Candidates candidates = new Candidates();
		CandidatesRepository candidatesRepository = new CandidatesArray(Candidate.SOMEONE);
		candidates.setCandidatesRepository(candidatesRepository);
		
		assertEquals(Arrays.asList(Candidate.SOMEONE, Candidate.SOMEONE), candidates.getAllCandidates());
	}

}