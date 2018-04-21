package azizollahi.app.socks.core.interfaces.flow;

import azizollahi.app.socks.core.config.Account;
import azizollahi.app.socks.core.interfaces.channels.IChannel;
import azizollahi.app.socks.core.interfaces.exceptions.NotAuthorizedException;

import java.io.IOException;

public interface IAuthorizationFlow {
	void process(IChannel srcChannel) throws NotAuthorizedException, IOException;
	Account getAuthorizedAccount();

}
