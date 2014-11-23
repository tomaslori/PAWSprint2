package ar.edu.itba.it.paw.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name = "distinctions")
public class Distinction {
	
	@Column(nullable = false)
	private String name;
	
	private boolean gotPrized;
	
	public Distinction() { }

	public Distinction(String name, boolean gotPrized) throws IllegalArgumentException {
		setName(name);
		setGotPrized(gotPrized);
	}

	
	public boolean getGotPrized() {
		return gotPrized;
	}

	private void setGotPrized(boolean gotPrized) {
		this.gotPrized = gotPrized;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) throws IllegalArgumentException {
		if (name == null)
			throw new IllegalArgumentException();
		else
			this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Distinction other = (Distinction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
