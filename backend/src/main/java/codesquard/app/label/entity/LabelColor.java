package codesquard.app.label.entity;

import codesquard.app.api.errors.exception.FontColorLabelException;

public enum LabelColor {
	LIGHT, DARK;

	public static final String DARK_STRING = "dark";
	public static final String LIGHT_STRING = "light";

	public String getNameToLowerCase() {
		return this.name().toLowerCase();
	}

	public static LabelColor chooseColor(final String color) {
		validateColor(color);

		if (color.equalsIgnoreCase(DARK_STRING)) {
			return DARK;
		}

		return LIGHT;
	}

	private static void validateColor(final String color) {
		if (!color.equalsIgnoreCase(LabelColor.DARK_STRING) && !color.equalsIgnoreCase(LabelColor.LIGHT_STRING)) {
			throw new FontColorLabelException();
		}
	}
}
