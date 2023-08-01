package codesquard.app.label.entity;

public enum LabelColor {
	LIGHT, DARK;

	public static final String DARK_STRING = "dark";
	public static final String LIGHT_STRING = "light";

	public String getNameToLowerCase() {
		return this.name().toLowerCase();
	}
}
