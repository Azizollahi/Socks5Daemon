package azizollahi.app.socks.core.flow;

import azizollahi.app.socks.core.Utility.LoggerUtility;
import azizollahi.app.socks.core.channeling.CommandReceiveHandler;
import azizollahi.app.socks.core.channeling.type.CommandDto;
import azizollahi.app.socks.core.channeling.type.EAddressType;
import azizollahi.app.socks.core.channeling.type.ECommand;
import azizollahi.app.socks.core.channeling.type.EResponse;
import azizollahi.app.socks.core.flow.handlers.BindHandler;
import azizollahi.app.socks.core.flow.handlers.ConnectHandler;
import azizollahi.app.socks.core.flow.handlers.UdpHandler;
import azizollahi.app.socks.core.interfaces.channels.IChannel;
import azizollahi.app.socks.core.interfaces.channels.IReceiverHandler;
import azizollahi.app.socks.core.interfaces.exceptions.AddressTypeNotSupportedException;
import azizollahi.app.socks.core.interfaces.exceptions.CommandNotSupportedException;
import azizollahi.app.socks.core.interfaces.exceptions.ConnectionRefuseException;
import azizollahi.app.socks.core.interfaces.exceptions.GeneralServerErrorException;
import azizollahi.app.socks.core.interfaces.flow.ICommandFlow;

import java.io.IOException;

public class CommandFlow implements ICommandFlow {

	private IChannel sourceChannel;
	private ICommandHandler handler;
	private CommandDto request;
	private LoggerUtility logger;
	IReceiverHandler receiverHandler;
	public CommandFlow(LoggerUtility logger) {
		this.logger = logger;
	}
	@Override
	public IChannel Process(IChannel sourceChannel) throws IOException {
		this.sourceChannel = sourceChannel;
		try {
			receiveRequest();
			CheckValidity();
			AbilityCheck();
			IChannel destination = process();
			if(!AnswerBack(EResponse.Succeeded))
				throw new Exception();
			return destination;
		} catch (CommandNotSupportedException e) {
			AnswerBack(EResponse.CommandNotSupported);
			logger.error("CommandNotSupported",e);
			throw new IOException(e);
		} catch (AddressTypeNotSupportedException e) {
			AnswerBack(EResponse.AddressTypeNotSupported);
			logger.error("AddressTypeNotSupported",e);
			throw new IOException(e);
		} catch (ConnectionRefuseException e) {
			AnswerBack(EResponse.ConnectionRefused);
			logger.error("ConnectionRefused",e);
			throw new IOException(e);
		} catch (GeneralServerErrorException | Exception e) {
			AnswerBack(EResponse.GeneralServerError);
			logger.error("GeneralServerError",e);
			throw new IOException(e);
		}
	}
	private void receiveRequest() throws IOException {
		receiverHandler = new CommandReceiveHandler();
		sourceChannel.receive(receiverHandler);
		request = (CommandDto) receiverHandler.getProperies().get("CommandDto");
	}
	private void CheckValidity() throws CommandNotSupportedException, AddressTypeNotSupportedException {
		if(request.getCommandType() == ECommand.Unknown)
			throw new CommandNotSupportedException();
		if(request.getAddressType() == EAddressType.Unknown)
			throw new AddressTypeNotSupportedException();
	}
	private void AbilityCheck() throws GeneralServerErrorException{
		handler = (new ConnectHandler()
				.setSuccessor(new BindHandler())
				.setSuccessor(new UdpHandler()));
		if(!handler.canProcess(request))
			throw new GeneralServerErrorException();
	}
	private IChannel process() throws ConnectionRefuseException {
		try {
			return handler.process(request);
		} catch (IOException exp) {
			throw new ConnectionRefuseException();
		}
	}
	private boolean AnswerBack(EResponse response) {
		byte[] responseBytes = request.buildResponse(response);
		try {
			sourceChannel.send(responseBytes);
			return true;
		} catch (IOException e) {
			try {
				sourceChannel.close();
			} catch (IOException e1) {
				logger.error("closing socket",e1);
			}
			logger.error("building response",e);
			return false;
		}
	}


}
