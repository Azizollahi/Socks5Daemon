package azizollahi.app.socks.core;

import azizollahi.app.socks.config.Daemon;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Socks5Daemon implements ISock5Daemon {
	private Semaphore waitingObject;
	private Daemon daemonConfig;
	private Logger logger;
	Socks5Daemon() {
		waitingObject = new Semaphore(0);
	}
	@Override
	public void init(String configPath) {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			PropertyConfigurator.configure(new FileInputStream("./log4j.properties"));
			logger = Logger.getLogger("Application");
			daemonConfig = mapper.readValue(new File(configPath), Daemon.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		Thread trd = new Thread(new Socks5Service(daemonConfig));
		logger.info("starting service '"+ daemonConfig.getName() +"' on ip: " + daemonConfig.getIp() + "and port: " + String.valueOf(daemonConfig.getPort()));
		trd.start();
		try {
			logger.info("application is lunched.");
			waitingObject.acquire();
		} catch (Exception e) {
			logger.fatal("Unexpected error cause the application to force stop!",e);
		}
		logger.warn("bye bye.");
	}
}
