package ro.contezi.novote.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;

import ro.contezi.novote.model.Candidate;
import ro.contezi.novote.repository.CandidatesRepository;

@ManagedBean(name = "candidates")
@SessionScoped
public class Candidates implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Candidate> allCandidates;
	
	@Inject
	private CandidatesRepository candidatesRepository;

	public CandidatesRepository getCandidatesRepository() {
		return candidatesRepository;
	}

	public void setCandidatesRepository(CandidatesRepository candidatesRepository) {
		this.candidatesRepository = candidatesRepository;
	}

	public List<Candidate> getAllCandidates() {
		if (allCandidates == null) {
			allCandidates = new ArrayList<>();
			allCandidates.add(Candidate.SOMEONE);
			allCandidates.addAll(candidatesRepository.getAllCandidates());
		}
		return allCandidates;
	}

}
