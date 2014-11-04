package ro.contezi.novote.repository;

import java.util.List;

import ro.contezi.novote.model.Candidate;

public interface CandidatesRepository {
	public List<Candidate> getAllCandidates();
}
