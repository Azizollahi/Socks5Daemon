package azizollahi.app.socks.core.interfaces.channels;

import java.io.StreamCorruptedException;
import java.util.HashMap;

public interface IReceiverHandler {
	int init();
	int read(byte[] data) throws StreamCorruptedException;
	HashMap<String, Object> getProperies();
}
