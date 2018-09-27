package azizollahi.app.socks.interfaces.flow;

import azizollahi.app.socks.interfaces.channels.IChannel;

import java.io.IOException;

public interface ICommandFlow {
	IChannel Process(IChannel sourceChannel) throws IOException;
}
