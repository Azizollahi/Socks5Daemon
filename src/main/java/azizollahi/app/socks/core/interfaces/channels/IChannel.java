package azizollahi.app.socks.core.interfaces.channels;

import azizollahi.app.socks.core.interfaces.exceptions.NoDataException;

import java.io.IOException;
import java.net.Socket;

public interface IChannel {
	void send(byte[] data) throws IOException;
	byte[] receive() throws IOException, NoDataException;
	void receive(IReceiverHandler receiverHandler) throws IOException;
	boolean isConnected();
	void open(String address, int port) throws IOException;

	void open(String address, int port, int timeout) throws IOException;

	void close() throws IOException;

	Socket getSocket();
}
