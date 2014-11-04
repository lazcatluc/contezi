package ro.contezi.novote.model;

import java.io.Serializable;

public class Candidate implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Candidate SOMEONE = new Candidate();	
	private String name = "";
	
	public Candidate() {
		
	}
	
	public Candidate(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

}
