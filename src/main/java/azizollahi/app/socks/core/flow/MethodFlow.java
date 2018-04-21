package azizollahi.app.socks.core.flow;

import azizollahi.app.socks.core.channeling.MethodReceiverHandler;
import azizollahi.app.socks.core.config.Account;
import azizollahi.app.socks.core.config.Daemon;
import azizollahi.app.socks.core.interfaces.channels.IChannel;
import azizollahi.app.socks.core.interfaces.channels.IReceiverHandler;
import azizollahi.app.socks.core.interfaces.exceptions.NotAuthorizedException;
import azizollahi.app.socks.core.interfaces.flow.IAuthorizationFlow;
import azizollahi.app.socks.core.interfaces.flow.IMethodFlow;
import azizollahi.app.socks.core.interfaces.types.Method;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MethodFlow implements IMethodFlow {
	private IReceiverHandler methodReceiver;
	private IChannel channel;
	private Account account;
	private Daemon daemonConfig;
	public MethodFlow(Daemon config) {
		daemonConfig = config;
	}
	@Override
	public void proccess(IChannel sourceChannel) throws IOException {
			channel = sourceChannel;
			receive();
			if(isMethodSupported()) {
				Method method = chooseAMethod();
				sendBack(method);
				if(method == Method.UserName_Password)
					authorizationProcess();
			}
			else
				sendError();
	}

	@Override
	public Account getAocount() {
		return account;
	}

	private void authorizationProcess() throws IOException {
		try {
			IAuthorizationFlow authorizationFlow = new AuthorizationFlow();
			authorizationFlow.process(channel);
			account = authorizationFlow.getAuthorizedAccount();
		}catch (NotAuthorizedException e) {
			e.printStackTrace();
			throw new IOException("user is not authorized!");
		}
	}

	private Method chooseAMethod() {
		HashMap dataSet = methodReceiver.getProperies();
		List<Method> methods = (List<Method>)dataSet.get("Methods");
		if(methods.stream().anyMatch(x->x == Method.UserName_Password) && daemonConfig.isAuthorization())
			return Method.UserName_Password;
		else if(!daemonConfig.isAuthorization())
			return Method.NoAuthentication;
		else
			return Method.NoAcceptableMethod;
	}

	private void sendBack(Method choosenMethod) throws IOException {
		byte[] response = new byte[2];
		response[0] = (byte)0x05;
		response[1] = (byte)choosenMethod.getValue();
		channel.send(response);
	}
	private void sendError() throws IOException {
		byte[] response = new byte[1];
		response[0] = (byte)Method.NoAcceptableMethod.getValue();
		channel.send(response);
		channel.close();
		throw new IOException("No method is supported!");
	}
	private void receive() throws IOException {
		methodReceiver = new MethodReceiverHandler();
		channel.receive(methodReceiver);
	}
	private boolean isMethodSupported() {
		List<Method> methods = (List<Method>) methodReceiver.getProperies().get("Methods");
		boolean isSupported = false;
		if(methods.size() > 0)
			isSupported = methods.stream().anyMatch(x -> (x == Method.NoAuthentication && !daemonConfig.isAuthorization()) ||
					(x == Method.UserName_Password && daemonConfig.isAuthorization())
			);
		else
			isSupported = false;
		return isSupported;
	}
}
