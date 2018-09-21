package azizollahi.app.socks.channeling;

import azizollahi.app.socks.interfaces.channels.IReceiverHandler;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class AuthenticationReceiveHandler implements IReceiverHandler {
	private State state;
	private int userNameLength;
	private int passwordLength;
	private HashMap<String, Object> result;

	public AuthenticationReceiveHandler() {
		userNameLength = 0;
		passwordLength = 0;
		result = new HashMap<>();
	}

	@Override
	public int init() {
		state = State.ReadUserNameLen;
		return 2;
	}

	@Override
	public int read(byte[] data) {
		switch (state) {
			case ReadUserNameLen:
				userNameLength = data[1];
				state = State.ReadUserName;
				return userNameLength;
			case ReadUserName:
				result.put("UserName",new String(data, StandardCharsets.UTF_8));
				state = State.ReadPasswordLen;
				return 1;
			case ReadPasswordLen:
				passwordLength = data[0];
				state = State.ReadPassword;
				return passwordLength;
			case ReadPassword:
				result.put("Password",new String(data, StandardCharsets.UTF_8));
				state = State.ReadUserNameLen;
				break;
		}
		return 0;
	}

	@Override
	public HashMap<String, Object> getProperies() {
		return result;
	}

	private enum State{
		ReadUserNameLen,
		ReadUserName,
		ReadPasswordLen,
		ReadPassword
	}
}
