package azizollahi.app.socks.channeling;

import azizollahi.app.socks.Utility.LoggerUtility;
import azizollahi.app.socks.interfaces.channels.IChannel;
import azizollahi.app.socks.interfaces.channels.IChannelBridge;
import azizollahi.app.socks.interfaces.exceptions.NoDataException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ChannelBridgeRunner implements Runnable {
	private IChannel channel;
	private IChannelBridge bridge;
	private LoggerUtility logger;

	public ChannelBridgeRunner(LoggerUtility logger, IChannel srcChannel, IChannelBridge bridge) {
		channel = srcChannel;
		this.bridge = bridge;
		this.logger = logger;
	}
	@Override
	public void run() {
		while(!bridge.isBroken()) {
			try {
				byte[] receivedBytes = channel.receive();
				bridge.send(channel, receivedBytes);
			} catch (SocketTimeoutException | NoDataException ignored) {
			} catch (IOException e) {
				logger.info(e.getMessage()+" "+ "breaking bridge!");
				bridge.Brake();
				return;
			}
		}
		logger.info("Mission ends");
	}
}
