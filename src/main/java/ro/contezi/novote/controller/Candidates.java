package ro.contezi.novote.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.repository.CandidatesRepository;

import java.io.Serializable;
import java.util.List;

@Named("candidates")
@SessionScoped
public class Candidates implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private CandidatesRepository candidatesRepository;

	public CandidatesRepository getCandidatesRepository() {
		return candidatesRepository;
	}

	public void setCandidatesRepository(CandidatesRepository candidatesRepository) {
		this.candidatesRepository = candidatesRepository;
	}

	public List<Candidate> getAllCandidates() {
		return candidatesRepository.getAllCandidates();
	}

}
