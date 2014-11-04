package ro.contezi.novote.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import ro.contezi.novote.config.Config;
import ro.contezi.novote.model.Candidate;

public class CandidatesArray implements CandidatesRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@Config
	private Candidate[] allCandidates;
	
	public CandidatesArray() {
	}
	
	public CandidatesArray(Candidate... allCandidates) {
		this.allCandidates = allCandidates;
	}
	
	@Override
	public List<Candidate> getAllCandidates() {
		return new ArrayList<>(Arrays.asList(allCandidates));
	}

}
