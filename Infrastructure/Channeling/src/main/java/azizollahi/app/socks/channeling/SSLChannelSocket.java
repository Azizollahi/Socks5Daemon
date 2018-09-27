package azizollahi.app.socks.channeling;

import javax.net.ssl.SSLServerSocket;
import java.io.IOException;

public class SSLChannelSocket extends SSLServerSocket {
	protected SSLChannelSocket() throws IOException {
	}

	@Override
	public String[] getEnabledCipherSuites() {
		return new String[0];
	}

	@Override
	public void setEnabledCipherSuites(String[] strings) {

	}

	@Override
	public String[] getSupportedCipherSuites() {
		return new String[0];
	}

	@Override
	public String[] getSupportedProtocols() {
		return new String[0];
	}

	@Override
	public String[] getEnabledProtocols() {
		return new String[0];
	}

	@Override
	public void setEnabledProtocols(String[] strings) {

	}

	@Override
	public void setNeedClientAuth(boolean b) {

	}

	@Override
	public boolean getNeedClientAuth() {
		return false;
	}

	@Override
	public void setWantClientAuth(boolean b) {

	}

	@Override
	public boolean getWantClientAuth() {
		return false;
	}

	@Override
	public void setUseClientMode(boolean b) {

	}

	@Override
	public boolean getUseClientMode() {
		return false;
	}

	@Override
	public void setEnableSessionCreation(boolean b) {

	}

	@Override
	public boolean getEnableSessionCreation() {
		return false;
	}
}
