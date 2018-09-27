package azizollahi.app.socks.interfaces.flow;

import azizollahi.app.socks.config.Account;
import azizollahi.app.socks.interfaces.channels.IChannel;

import java.io.IOException;

public interface IMethodFlow {
	void proccess(IChannel sourceChannel) throws IOException;
	Account getAocount();
}
