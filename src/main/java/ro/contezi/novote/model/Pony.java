package ro.contezi.novote.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pony implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int ponyId;

	public int getPonyId() {
		return ponyId;
	}

	public void setPonyId(int ponyId) {
		this.ponyId = ponyId;
	}
}
