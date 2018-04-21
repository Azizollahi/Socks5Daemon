package azizollahi.app.socks.core.interfaces.types;

public enum Method {
	NoAuthentication(0),
	UserName_Password(2),
	NoAcceptableMethod(0xFF);
	private int value;
	Method(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public static Method pase(int value){
		switch (value) {
			case 0:
				return NoAuthentication;
			case 2:
				return UserName_Password;
		}
		return NoAcceptableMethod;
	}
}
