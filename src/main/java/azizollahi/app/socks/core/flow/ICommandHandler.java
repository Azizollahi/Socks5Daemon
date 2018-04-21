package azizollahi.app.socks.core.flow;

import azizollahi.app.socks.core.channeling.type.CommandDto;
import azizollahi.app.socks.core.interfaces.channels.IChannel;

import java.io.IOException;

public interface ICommandHandler {
	boolean canProcess(CommandDto receivedCommand);
	IChannel process(CommandDto receivedCommand) throws IOException;
	ICommandHandler setSuccessor(ICommandHandler successor);
}
