package wojtek.pockettrainer.models.enums;


/**
 * @author Wojtek Kolendo
 */
public enum Units {

	METRIC(0),
	IMPERIAL(1);

	private final int id;

	Units(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
