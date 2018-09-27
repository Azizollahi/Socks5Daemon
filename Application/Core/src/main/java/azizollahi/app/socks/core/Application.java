package azizollahi.app.socks.core;

public class Application {
	public static void main(String[] args) {
		ISock5Daemon daemon = new Socks5Daemon();
		daemon.init("./config.yaml");
		daemon.run();
	}
}
