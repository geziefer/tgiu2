package geziefer.tgiu2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "Game.findAll", query = "SELECT g FROM Game g ORDER BY g.name"),
		@NamedQuery(name = "Game.findByName", query = "SELECT g FROM Game g WHERE g.name = :name") })
public class Game extends Base {
	@Column(unique = true)
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private GameValue value;

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
