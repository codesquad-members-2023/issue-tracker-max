package codesquard.app.milestone.entity;

public enum MilestoneStatus {
	OPENED, CLOSED;

	public static final String OPENED_STRING = "opened";
	public static final String CLOSED_STRING = "closed";

	public String getName() {
		return this.name();
	}

	public static MilestoneStatus chooseStatus(final String openedString, final String closedString) {
		if (openedString.equalsIgnoreCase(OPENED_STRING)) {
			return MilestoneStatus.OPENED;
		}

		if (closedString.equalsIgnoreCase(CLOSED_STRING)) {
			return MilestoneStatus.CLOSED;
		}

		return MilestoneStatus.OPENED;
	}
}
