package ro.contezi.novote.model;

public class Candidate {

	public static final Candidate SOMEONE = new Candidate() {
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

}
