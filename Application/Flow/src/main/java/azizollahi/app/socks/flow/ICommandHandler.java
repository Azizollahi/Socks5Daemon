package azizollahi.app.socks.flow;

import azizollahi.app.socks.channeling.type.CommandDto;
import azizollahi.app.socks.interfaces.channels.IChannel;

import java.io.IOException;

public interface ICommandHandler {
	boolean canProcess(CommandDto receivedCommand);
	IChannel process(CommandDto receivedCommand) throws IOException;
	ICommandHandler setSuccessor(ICommandHandler successor);
}
