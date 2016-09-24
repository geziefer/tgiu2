package geziefer.tgiu2.entity;

import java.util.ResourceBundle;

public enum GameValue {
	SMALL(1), MEDIUM(2), LARGE(3);

	int value;

	GameValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("messages").getString("games.value." + value);
	}

}
