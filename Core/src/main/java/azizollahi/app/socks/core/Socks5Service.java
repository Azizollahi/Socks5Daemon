package azizollahi.app.socks.core;

import azizollahi.app.socks.config.Daemon;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Socks5Service implements Runnable {
	ServerSocket socket;
	Boolean isWorking;
	ExecutorService executor;
	Daemon daemonConfig;
	public Socks5Service(Daemon config) {
		isWorking = false;
		daemonConfig = config;
	}

	@Override
	public void run() {
		Logger logger = Logger.getLogger(daemonConfig.getName());
		try {
			socket = new ServerSocket();
			socket.bind(new InetSocketAddress(daemonConfig.getIp(), daemonConfig.getPort()));
			logger.trace("bound to " + daemonConfig.getIp() + ":" + String.valueOf(daemonConfig.getPort()));
			executor = Executors.newFixedThreadPool(daemonConfig.getThreadMax());
			logger.trace("thread pool is built.");
			isWorking = true;
			while(isWorking && socket.isBound()) {
				Socket client = socket.accept();
				logger.info("New client is joined from "+
						((InetSocketAddress)client.getRemoteSocketAddress()).getHostName() +
						":" +
						String.valueOf(((InetSocketAddress)client.getRemoteSocketAddress()).getPort()));
				client.setSoTimeout(daemonConfig.getTimeout());
				executor.submit(new ClientService(daemonConfig,client));
				logger.info("sent to be served.");
			}
		} catch (IOException e) {
			logger.fatal("Exiting service",e);
		}
	}
}
