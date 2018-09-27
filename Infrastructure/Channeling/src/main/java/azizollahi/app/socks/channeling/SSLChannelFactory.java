package azizollahi.app.socks.channeling;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

public class SSLChannelFactory {
	private SSLChannelSocket socket;
	public SSLChannelFactory() throws IOException {
		socket = new SSLChannelSocket();
	}
	public void bind(SocketAddress socketAddress) throws IOException {
		socket.bind(socketAddress);
	}
	public Socket accept() throws IOException {
		return socket.accept();
	}
}
