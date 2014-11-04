package ro.contezi.novote.model;

import java.io.Serializable;

public class Candidate implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Candidate SOMEONE = new Candidate() {
		private static final long serialVersionUID = 1L;

		@Override
		public String getName() {
			return "SOMEONE";
		}
	};
	
	private String name;

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
