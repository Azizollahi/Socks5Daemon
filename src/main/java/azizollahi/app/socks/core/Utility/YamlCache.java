package azizollahi.app.socks.core.Utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class YamlCache<E> {
	private E data;
	private ObjectMapper mapper;
	private Class<E> dataType;
	private ReentrantLock lock;
	private Date lastTime;
	private String path;
	public YamlCache(Class<E> dataType, String resourcePath) {
		path = resourcePath;
		lock = new ReentrantLock();
		YAMLFactory yamlFactory = new YAMLFactory();
		mapper = new ObjectMapper(yamlFactory);
		this.dataType = dataType;
	}

	public E getCache() {
		if(data == null) {
			reNew();
		} else {
			if(isTimeToRenew())
				reNew();
		}
		return data;
	}
	private boolean isTimeToRenew() {
		if(lastTime == null)
			return true;
		return (new Date().getTime() - lastTime.getTime()) > 10000;
	}
	private void reNew() {
		lock.lock();
		try {
			data = mapper.readValue(new File(path), dataType);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
