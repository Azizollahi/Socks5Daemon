package azizollahi.app.socks.core.flow.handlers;

import azizollahi.app.socks.core.channeling.type.CommandDto;
import azizollahi.app.socks.core.flow.ICommandHandler;
import azizollahi.app.socks.core.interfaces.channels.IChannel;

import java.io.IOException;

public class UdpHandler implements ICommandHandler {
	@Override
	public boolean canProcess(CommandDto receivedCommand) {
		return false;
	}

	@Override
	public IChannel process(CommandDto receivedCommand) throws IOException {
		return null;
	}

	@Override
	public ICommandHandler setSuccessor(ICommandHandler successor) {
		return this;
	}
}
