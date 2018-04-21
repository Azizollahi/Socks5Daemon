package azizollahi.app.socks.core.flow.handlers;

import azizollahi.app.socks.core.channeling.Channel;
import azizollahi.app.socks.core.channeling.type.CommandDto;
import azizollahi.app.socks.core.channeling.type.EAddressType;
import azizollahi.app.socks.core.channeling.type.ECommand;
import azizollahi.app.socks.core.flow.ICommandHandler;
import azizollahi.app.socks.core.interfaces.channels.IChannel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectHandler implements ICommandHandler {
	ICommandHandler handler;
	public ICommandHandler setSuccessor(ICommandHandler successor){
		handler = successor;
		return this;
	}

	@Override
	public boolean canProcess(CommandDto receivedCommand) {
		if(receivedCommand.getCommandType() == ECommand.Connect)
			return true;
		else if(handler != null && handler.canProcess(receivedCommand))
			return true;
		else
			return false;
	}

	@Override
	public IChannel process(CommandDto receivedCommand) throws IOException {
		IChannel destinationChannel;
		if(handler != null && handler.canProcess(receivedCommand)) {
			destinationChannel = handler.process(receivedCommand);
		} else {
			Socket destinationSocket = new Socket();
			if(receivedCommand.getAddressType() == EAddressType.HostNameAddress)
				connectByHostName(destinationSocket, receivedCommand);
			else
				connectByAddress(destinationSocket, receivedCommand);
			destinationChannel = new Channel(destinationSocket);
		}
		return destinationChannel;
	}
	private void connectByHostName(Socket destinationSocket, CommandDto receivedCommand) throws IOException {
		destinationSocket.connect(
				new InetSocketAddress(
						new String(receivedCommand.getAddrress()),
						receivedCommand.getPort()));
	}
	private void connectByAddress(Socket destinationSocket, CommandDto receivedCommand) throws IOException {
		destinationSocket.connect(
				new InetSocketAddress(InetAddress.getByAddress(receivedCommand.getAddrress()),
						receivedCommand.getPort()));
	}
}
