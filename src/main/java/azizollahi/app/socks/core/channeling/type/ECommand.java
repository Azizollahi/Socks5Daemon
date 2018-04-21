package azizollahi.app.socks.core.channeling.type;

public enum ECommand {
	Connect(0x01),
	Bind(0x02),
	Udp(0x03),
	Unknown(0x04);
	private int value;
	ECommand(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static ECommand Parse(int command) {
		switch (command) {
			case 1:
				return ECommand.Connect;
			case 2:
				return ECommand.Bind;
			case 3:
				return ECommand.Udp;
			default:
				return ECommand.Unknown;
		}
	}
}
