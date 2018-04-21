package azizollahi.app.socks.core.Utility;

import azizollahi.app.socks.core.config.Account;
import org.apache.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoggerUtility {
	private Account account;
	private String logId;
	private Logger logger;
	private static int id;
	private static Lock lock = new ReentrantLock();
	public static String GenerateNewId() {
		String idStr = "";
		lock.lock();
		id++;
		idStr =String.format("%8x",id);
		lock.unlock();
		return idStr;
	}

	public LoggerUtility(Logger logger) {
		this.logger = logger;
		logId = GenerateNewId();
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private String getPrefix(){
		return "["+getUserName()+"]"+"["+ logId +"]";
	}
	private String getUserName() {
		if(account != null && account.getUserName() != null)
			return account.getUserName();
		else
			return "";
	}

	public void trace(String message){
		logger.trace(getPrefix() + message);
	}
	public void debug(String message) {
		logger.debug(getPrefix() + message);
	}
	public void info(String message){
		logger.info(getPrefix() + message);
	}
	public void warn(String message){
		logger.warn(getPrefix() + message);
	}
	public void warn(String message,Throwable throwable){
		logger.warn(getPrefix() + message,throwable);
	}
	public void error(String message, Throwable throwable){
		logger.error(getPrefix() + message,throwable);
	}
	public void fatal(String message, Throwable throwable){
		logger.fatal(getPrefix() + message,throwable);
	}

}
