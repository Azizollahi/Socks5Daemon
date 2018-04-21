package azizollahi.app.socks.core.interfaces.flow;

import azizollahi.app.socks.core.interfaces.channels.IChannel;

import java.io.IOException;

public interface ICommandFlow {
	IChannel Process(IChannel sourceChannel) throws IOException;
}
