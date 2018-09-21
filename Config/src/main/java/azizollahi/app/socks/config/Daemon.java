package azizollahi.app.socks.config;

public class Daemon {
	private String ip;
	private int port;
	private String name;
	private int threadMax;
	private int timeout;
	private boolean authorization;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getThreadMax() {
		return threadMax;
	}

	public void setThreadMax(int threadMax) {
		this.threadMax = threadMax;
	}

	public boolean isAuthorization() {
		return authorization;
	}

	public void setAuthorization(boolean authorization) {
		this.authorization = authorization;
	}
}
