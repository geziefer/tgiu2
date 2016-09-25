package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "Player.findAll", query = "SELECT p FROM Player p ORDER BY p.name"),
		@NamedQuery(name = "Player.findByName", query = "SELECT p FROM Player p WHERE p.name = :name") })
public class Player {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(unique = true)
	private String name;

	@Column
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

}
