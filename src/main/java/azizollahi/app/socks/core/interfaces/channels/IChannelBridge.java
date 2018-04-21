package azizollahi.app.socks.core.interfaces.channels;


public interface IChannelBridge {
	void attach(IChannel channel);
	void send(IChannel sourceChannel, byte[] sendingData);
	boolean isBroken();
	void Brake();
}
