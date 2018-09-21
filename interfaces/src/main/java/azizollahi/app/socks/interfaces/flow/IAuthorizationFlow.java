package azizollahi.app.socks.interfaces.flow;

import azizollahi.app.socks.config.Account;
import azizollahi.app.socks.interfaces.channels.IChannel;
import azizollahi.app.socks.interfaces.exceptions.NotAuthorizedException;

import java.io.IOException;

public interface IAuthorizationFlow {
	void process(IChannel srcChannel) throws NotAuthorizedException, IOException;
	Account getAuthorizedAccount();

}
