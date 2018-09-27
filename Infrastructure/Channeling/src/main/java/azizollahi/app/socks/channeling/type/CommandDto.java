package azizollahi.app.socks.channeling.type;


import java.nio.ByteBuffer;

public class CommandDto {
	private EScoksVersion version;
	private EAddressType addressType;
	private byte[] addrress;
	private ECommand commandType;
	private int port;

	public EScoksVersion getVersion() {
		return version;
	}

	public void setVersion(EScoksVersion version) {
		this.version = version;
	}

	public EAddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(EAddressType addressType) {
		this.addressType = addressType;
	}

	public byte[] getAddrress() {
		return addrress;
	}

	public void setAddrress(byte[] ipAddress) {
		this.addrress = ipAddress;
	}

	public ECommand getCommandType() {
		return commandType;
	}

	public void setCommandType(ECommand commandType) {
		this.commandType = commandType;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public byte[] buildResponse(EResponse response) {
		int totalSize = 6 + getAddrress().length;
		if(getAddressType() == EAddressType.HostNameAddress)
			totalSize += 1;
		byte[] responseBytes = new byte[totalSize];
		responseBytes[0] = 0x05;
		responseBytes[1] = (byte) response.getValue();
		responseBytes[3] = (byte) getAddressType().getType();
		int addressIndx = 4;
		if(getAddressType() == EAddressType.HostNameAddress) {
			responseBytes[4] = (byte) getAddrress().length;
			addressIndx = 5;
		}

		System.arraycopy(getAddrress(),0,responseBytes,addressIndx, getAddrress().length);
		System.arraycopy(ByteBuffer.allocate(2).putShort((short)getPort()).array(),0,responseBytes,totalSize-3, 2);
		return responseBytes;
	}
}
