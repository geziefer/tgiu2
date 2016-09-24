package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g ORDER BY g.name")
public class Game {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(unique = true)
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private GameValue value;

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

	public GameValue getValue() {
		return value;
	}

	public void setValue(GameValue value) {
		this.value = value;
	}

}
