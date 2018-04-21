package azizollahi.app.socks.core.interfaces.flow;

import azizollahi.app.socks.core.config.Account;
import azizollahi.app.socks.core.interfaces.channels.IChannel;
import azizollahi.app.socks.core.interfaces.exceptions.NoDataException;

import java.io.IOException;

public interface IMethodFlow {
	void proccess(IChannel sourceChannel) throws IOException;
	Account getAocount();
}
