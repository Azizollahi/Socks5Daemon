package azizollahi.app.socks.channeling;

import azizollahi.app.socks.interfaces.channels.IReceiverHandler;
import azizollahi.app.socks.interfaces.types.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MethodReceiverHandler implements IReceiverHandler {
	private HashMap<String,Object> dataMap;
	private boolean isInitialized;
	public MethodReceiverHandler() {
		dataMap = new HashMap<>();
		isInitialized = false;
	}
	@Override
	public int init() {
		return 2;
	}

	@Override
	public int read(byte[] data) {
		if(!isInitialized) {
			isInitialized = true;
			return data[1];
		}
		List<Method> methods = new ArrayList<>();
		for (byte theByte : data)
			methods.add(Method.pase(theByte));
		dataMap.put("Methods", methods);
		return 0;
	}

	@Override
	public HashMap<String, Object> getProperies() {
		return dataMap;
	}
}
