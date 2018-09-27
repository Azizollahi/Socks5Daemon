package azizollahi.app.socks.channeling;

import azizollahi.app.socks.channeling.type.CommandDto;
import azizollahi.app.socks.channeling.type.EAddressType;
import azizollahi.app.socks.channeling.type.ECommand;
import azizollahi.app.socks.channeling.type.EScoksVersion;
import azizollahi.app.socks.interfaces.channels.IReceiverHandler;

import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class CommandReceiveHandler implements IReceiverHandler {
	private HashMap<String,Object> dataMap;
	private State state;
	private CommandDto dto;
	public CommandReceiveHandler() {
		dataMap = new HashMap<>();
		dto = new CommandDto();
	}
	public int init() {
		state = State.Initial;
		return 4;
	}
	public int read(byte[] data) throws StreamCorruptedException {
		switch (state) {
			case Initial:
				if(data[2] != 0 || data[0] != 5)
					throw new StreamCorruptedException();
				dto.setVersion(EScoksVersion.Version5);
				dto.setCommandType(ECommand.Parse(data[1]));
				dto.setAddressType(EAddressType.Parse(data[3]));
				return getDataType(data);
			case AddressLength:
				state = State.Address;
				return data[0];
			case Address:
				state = State.Port;
				dto.setAddrress(data);
				return 2;
			case Port:
				dto.setPort(ByteBuffer.wrap(data).getShort());
				dataMap.put("CommandDto",dto);
				return 0;
		}
		return 0;
	}
	public HashMap<String, Object> getProperies() {
		return dataMap;
	}

	private enum State{
		Initial,
		AddressLength,
		Address,
		Port
	}
	private  int getDataType(byte[] data) {
		switch (data[3]) {
			case 1:
				state = State.Address;
				return 4;
			case 3:
				state = State.AddressLength;
				return 1;
			case 4:
				state = State.Address;
				return 16;
		}
		return 0;
	}
}
