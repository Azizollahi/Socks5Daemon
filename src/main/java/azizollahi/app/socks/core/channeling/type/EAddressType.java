package azizollahi.app.socks.core.channeling.type;

public enum EAddressType {
	V4Address(1),
	HostNameAddress(3),
	V6Address(4),
	Unknown(0xFF);
	private int type;
	EAddressType(int value) {
		type = value;
	}
	public int getType() {
		return type;
	}

	public static EAddressType Parse(int command) {
		switch (command) {
			case 1:
				return EAddressType.V4Address;
			case 3:
				return EAddressType.HostNameAddress;
			case 4:
				return EAddressType.V6Address;
		}
		return EAddressType.Unknown;
	}
}
