package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({ @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p ORDER BY p.name"),
		@NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name") })
public class Player extends Base {
	@Column(unique = true)
	private String name;

	@Column
	@JsonIgnore
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private Role role;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean hasRole(Role role) {
		return this.role.equals(role);
	}

}
