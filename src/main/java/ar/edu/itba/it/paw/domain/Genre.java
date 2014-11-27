package ar.edu.itba.it.paw.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
@Table(name = "genres")
public class Genre {
	
	@Column(nullable = false)
	private String name;
	
	Genre() { }

	public Genre(String name) throws IllegalArgumentException {
		setName(name);
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
		Genre other = (Genre) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
