package pandey.ujjwal.e_comm_backend.enums;

public enum Provider {
	LOCAL("LOCAL"), GOOGLE("GOOGLE"), GITHUB("GITHUB");

	public final String label;

	private Provider(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}