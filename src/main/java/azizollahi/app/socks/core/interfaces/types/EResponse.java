package azizollahi.app.socks.core.interfaces.types;

public enum EResponse {
	Succeeded(0x00),
	GeneralSocksServerError(0x01),
	ConnectionNotAllowedByRuleset(0x02),
	NetworkUnreachable(0x03),
	HostUnreachable(0x04),
	ConnectionRefused(0x05),
	TTLExpired(0x06),
	CommandNotSupported(0x07),
	AddressTypeNotSupported(0x08),
	UnAssigned(0xFF);

	private int value;
	EResponse(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}
