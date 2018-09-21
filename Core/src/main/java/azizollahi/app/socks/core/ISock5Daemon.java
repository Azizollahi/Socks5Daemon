package azizollahi.app.socks.core;

public interface ISock5Daemon {
	void init(String configPath);

	void run();
}
