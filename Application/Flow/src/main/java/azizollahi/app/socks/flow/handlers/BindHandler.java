package azizollahi.app.socks.flow.handlers;

import azizollahi.app.socks.channeling.type.CommandDto;
import azizollahi.app.socks.flow.ICommandHandler;
import azizollahi.app.socks.interfaces.channels.IChannel;

import java.io.IOException;

public class BindHandler implements ICommandHandler {
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
