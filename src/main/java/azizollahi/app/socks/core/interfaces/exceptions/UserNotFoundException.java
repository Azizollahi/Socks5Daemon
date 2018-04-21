package azizollahi.app.socks.core.interfaces.exceptions;

public class UserNotFoundException extends Throwable {
	public UserNotFoundException(String userName) {
		super("user '" + userName + "' not found");
	}
}
