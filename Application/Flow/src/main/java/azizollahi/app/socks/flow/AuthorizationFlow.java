package azizollahi.app.socks.flow;

import azizollahi.app.socks.Utility.YamlCache;
import azizollahi.app.socks.channeling.AuthenticationReceiveHandler;
import azizollahi.app.socks.config.Account;
import azizollahi.app.socks.config.Authentication;
import azizollahi.app.socks.flow.types.EAuthenticationResponseStatus;
import azizollahi.app.socks.interfaces.channels.IChannel;
import azizollahi.app.socks.interfaces.channels.IReceiverHandler;
import azizollahi.app.socks.interfaces.exceptions.NotAuthorizedException;
import azizollahi.app.socks.interfaces.exceptions.UserNotFoundException;
import azizollahi.app.socks.interfaces.flow.IAuthorizationFlow;

import java.io.IOException;
import java.util.Optional;

public class AuthorizationFlow implements IAuthorizationFlow {
	Account account;
	private IReceiverHandler receiverHandler;
	private static YamlCache<Authentication> cache = new YamlCache<>(Authentication.class,"./accounts.yaml");
	@Override
	public void process(IChannel srcChannel) throws NotAuthorizedException, IOException {
		receiverHandler = new AuthenticationReceiveHandler();
		srcChannel.receive(receiverHandler);
		byte[] result = new byte[2];
		if(authenticate()) {
			result[0] = 0x05;
			result[1] = (byte) EAuthenticationResponseStatus.Succeeded.getStatus();
			srcChannel.send(result);
		} else {
			result[0] = 0x05;
			result[1] = (byte) EAuthenticationResponseStatus.Failed.getStatus();
			srcChannel.send(result);
			throw new NotAuthorizedException("");
		}
	}

	@Override
	public Account getAuthorizedAccount() {
		return account;
	}

	private boolean authenticate(){
		try {
			String userName = (String) receiverHandler.getProperies().get("UserName");
			String password = (String) receiverHandler.getProperies().get("Password");
			if(userName == null || password == null)
				return false;
			account = findUser(userName,password);
			return true;
		} catch (UserNotFoundException e) {
			return false;
		}
	}
	private Account findUser(String userName, String password) throws UserNotFoundException {
		Authentication authentication= cache.getCache();
		Optional<Account> account = authentication.getAccounts().stream().filter(x-> x.getUserName().equals(userName) && x.getPassword().equals(password)).findFirst();
		if(!account.isPresent()) {
			throw new UserNotFoundException(userName);
		}
		return account.get();
	}
}
