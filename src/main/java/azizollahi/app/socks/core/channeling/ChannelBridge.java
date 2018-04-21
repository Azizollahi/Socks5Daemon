package azizollahi.app.socks.core.channeling;

import azizollahi.app.socks.core.interfaces.channels.IChannel;
import azizollahi.app.socks.core.interfaces.channels.IChannelBridge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChannelBridge implements IChannelBridge {
	private List<IChannel> channels;
	public ChannelBridge() {
		channels = new ArrayList<>();
	}
	@Override
	public void attach(IChannel channel) {
		channels.add(channel);
	}

	@Override
	public void send(IChannel sourceChannel, byte[] sendingData) {
		channels.stream()
				.filter(channel-> !channel.equals(sourceChannel))
				.forEach(channel -> {
					try {
						channel.send(sendingData);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});

	}

	@Override
	public boolean isBroken() {
		boolean status = channels.stream().anyMatch(channel -> !channel.isConnected());
		return status;
	}

	public void Brake() {
		channels.stream().forEach(channel -> {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
