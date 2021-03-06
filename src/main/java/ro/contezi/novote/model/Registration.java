package ro.contezi.novote.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Registration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final Registration SOMETHING = new Registration(Voter.SOMEONE, Candidate.SOMEONE);
	@Id
	@OneToOne(cascade=CascadeType.PERSIST)
	private Voter user;
	@ManyToOne
	private Candidate candidate;
	
	public Registration() {
		
	}
	
	public Registration(Voter someUser, Candidate someCandidate) {
		this.user = someUser;
		this.candidate = someCandidate;
	}
	
	public Voter getUser() {
		return user;
	}
	public void setUser(Voter user) {
		this.user = user;
	}
	public Candidate getCandidate() {
		return candidate;
	}
	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Registration other = (Registration) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Registration for " + user + " voting for " + candidate;
	}
	
	
}
