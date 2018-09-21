package azizollahi.app.socks.channeling;

import azizollahi.app.socks.interfaces.channels.IChannel;
import azizollahi.app.socks.interfaces.channels.IReceiverHandler;
import azizollahi.app.socks.interfaces.exceptions.NoDataException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Channel implements IChannel {
	private Socket socket;
	private boolean isClosed;
	private byte[] buffer;
	public Channel() {
		isClosed = true;
		init();
	}
	public Channel(Socket socket) {
		isClosed = false;
		this.socket = socket;
		init();
	}
	private void init() {
		buffer = new byte[8196];
	}

	@Override
	public void send(byte[] data) throws IOException {
		if(isClosed)
			return;
		socket.getOutputStream().write(data);
	}

	@Override
	public byte[] receive() throws IOException, NoDataException {
		if(!isClosed) {
			int readBytes = socket.getInputStream().read(buffer);
			if(readBytes < 0) {
				close();
				throw new IOException("peer socket is closed");
			}
			byte[] result = new byte[readBytes];
			System.arraycopy(buffer,0,result,0,readBytes);
			return result;
		}
		throw new NoDataException("No data on read.");
	}

	@Override
	public void receive(IReceiverHandler receiverHandler) throws IOException {
		int readLen = receiverHandler.init();
		byte[] bytes = new byte[readLen];
		while(readLen != 0) {
			int rr = socket.getInputStream().readNBytes(bytes,0,readLen);
			readLen = receiverHandler.read(bytes);
			bytes = new byte[readLen];
		}
	}

	@Override
	public boolean isConnected() {
		if(socket.isConnected() && !socket.isClosed() && !isClosed)
			return  true;
		return false;
	}

	@Override
	public void open(String address, int port) throws IOException {
		isClosed = false;
		socket = new Socket();
		socket.connect(InetSocketAddress.createUnresolved(address,port));
	}

	@Override
	public void open(String address, int port, int timeout) throws IOException {
		isClosed = false;
		socket = new Socket();
		socket.connect(InetSocketAddress.createUnresolved(address,port),timeout);
	}

	@Override
	public void close() throws IOException {
		isClosed = true;

		socket.close();
	}

	@Override
	public Socket getSocket() {
		return socket;
	}
}
