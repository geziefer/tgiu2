package geziefer.tgiu2.entity;

import java.util.ResourceBundle;

public enum GameValue {
	LARGE(3), MEDIUM(2), SMALL(1);

	int value;

	GameValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return ResourceBundle.getBundle("messages").getString("games.value." + value);
	}

}
