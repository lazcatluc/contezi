package ro.contezi.novote.model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final User SOMEONE = new User();
	
	private String name = "";
	private String email = "";
	private String cnp = "";
	private String city = "";
	private String country = "";

	private User pairedVoter = User.SOMEONE;
	
	public User() {
		
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCnp() {
		return cnp;
	}
	public void setCnp(String cnp) {
		this.cnp = cnp;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.toLowerCase().hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equalsIgnoreCase(other.email))
			return false;
		return true;
	}

	public User getPairedVoter() {
		return pairedVoter ;
	}
	
	public void setPairedVoter(User pairedVoter) {
		this.pairedVoter = pairedVoter;
	}

	public boolean hasPair() {
		return !SOMEONE.equals(pairedVoter);
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + "]";
	}

	
}
