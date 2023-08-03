package codesquard.app.label.entity;

public enum LabelColor {
	LIGHT, DARK;

	public static final String DARK_STRING = "dark";
	public static final String LIGHT_STRING = "light";

	public String getNameToLowerCase() {
		return this.name().toLowerCase();
	}

	public static LabelColor chooseColor(final String color) {
		if (color.equalsIgnoreCase(DARK_STRING)) {
			return DARK;
		}

		return LIGHT;
	}

	public static boolean validateColor(final String color) {
		if (!color.equalsIgnoreCase(LabelColor.DARK_STRING) && !color.equalsIgnoreCase(LabelColor.LIGHT_STRING)) {
			throw new RuntimeException("임시");
		}

		return true;
	}
}
