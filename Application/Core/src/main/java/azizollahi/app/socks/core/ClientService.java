package azizollahi.app.socks.core;


import azizollahi.app.socks.Utility.LoggerUtility;
import azizollahi.app.socks.channeling.Channel;
import azizollahi.app.socks.channeling.ChannelBridge;
import azizollahi.app.socks.channeling.ChannelBridgeRunner;
import azizollahi.app.socks.config.Daemon;
import azizollahi.app.socks.flow.CommandFlow;
import azizollahi.app.socks.flow.MethodFlow;
import azizollahi.app.socks.interfaces.channels.IChannel;
import azizollahi.app.socks.interfaces.channels.IChannelBridge;
import azizollahi.app.socks.interfaces.flow.ICommandFlow;
import azizollahi.app.socks.interfaces.flow.IMethodFlow;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientService implements Runnable {
	private IChannel clientChannel;
	private IMethodFlow methodFlow;
	private ICommandFlow commandFlow;
	private LoggerUtility logger;
	ClientService(Daemon config, Socket clientSocket) {
		this.clientChannel = new Channel(clientSocket);
		logger = new LoggerUtility(Logger.getLogger("MainService"));
		this.methodFlow = new MethodFlow(config);
		this.commandFlow = new CommandFlow(logger);
	}

	@Override
	public void run() {
		try {
			processMethodFlow();
			logger.trace("methodFlow is passed!");
			IChannel destinationChannel = commandFlow.Process(clientChannel);
			logger.debug("command flow is passed!");
			destinationChannel.getSocket().setSoTimeout(clientChannel.getSocket().getSoTimeout());
			IChannelBridge channelBridge = new ChannelBridge();
			channelBridge.attach(clientChannel);
			channelBridge.attach(destinationChannel);
			Thread srcTrd = new Thread(new ChannelBridgeRunner(logger, clientChannel, channelBridge));
			Thread dstTrd = new Thread(new ChannelBridgeRunner(logger, destinationChannel, channelBridge));
			srcTrd.start();
			dstTrd.start();
			logger.info("The channel bridge is started serving");
		} catch (Exception e) {
			logger.error("Service error", e);
		}
	}
	private void processMethodFlow() throws Exception {
		try{
			methodFlow.proccess(clientChannel);
			logger.setAccount(methodFlow.getAocount());
		} catch (Exception e) {
			try {
				if(clientChannel != null && clientChannel.isConnected())
					clientChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				throw new IOException();
			}
			e.printStackTrace();
			throw new IOException();
		}
	}
}
