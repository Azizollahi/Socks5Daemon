package azizollahi.app.socks.core;

import azizollahi.app.socks.core.Utility.YamlCache;
import azizollahi.app.socks.core.config.Authentication;

public class Application {
	public static void main(String[] args) {
		ISock5Daemon daemon = new Socks5Daemon();
		daemon.init("./config.yaml");
		daemon.run();
	}
}
