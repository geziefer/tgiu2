package geziefer.tgiu2.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Base {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return (getId() != null) ? (getClass().getSimpleName().hashCode() + getId().hashCode()) : super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return (other != null && getId() != null && other.getClass().isAssignableFrom(getClass())
				&& getClass().isAssignableFrom(other.getClass())) ? getId().equals(((Base) other).getId())
						: (other == this);
	}

	@Override
	public String toString() {
		return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
	}
}
