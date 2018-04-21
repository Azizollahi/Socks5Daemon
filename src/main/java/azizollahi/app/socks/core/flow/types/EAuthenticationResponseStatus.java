package azizollahi.app.socks.core.flow.types;

public enum EAuthenticationResponseStatus {
	Succeeded(0x00),
	Failed(0xFF);
	private int status;
	private EAuthenticationResponseStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
}
