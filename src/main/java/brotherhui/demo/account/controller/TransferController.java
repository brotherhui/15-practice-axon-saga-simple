package brotherhui.demo.account.controller;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

import java.util.UUID;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import brotherhui.demo.account.domain.command.CreateBankAccountCommand;
import brotherhui.demo.account.domain.command.CreateBankTransferCommand;
import brotherhui.demo.account.domain.command.WithdrawMoneyCommand;



@RestController
@RequestMapping("/transfer")
public class TransferController {

	private final static Logger log = LoggerFactory.getLogger(TransferController.class);
	@Autowired
	private CommandBus commandBus;
	
	@GetMapping("/{source}/{target}/{amount}")
	public String transferMoney(@PathVariable("source") String sourceAccountId,@PathVariable("target") String targetAccountId, @PathVariable("amount") Long amount) {
		final StringBuilder resultBuilder = new StringBuilder();
		String txnId=UUID.randomUUID().toString();
		commandBus.dispatch(asCommandMessage(new CreateBankTransferCommand(txnId, sourceAccountId,targetAccountId,amount)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("money tranfered successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("money tranfer failed").append(" :: ").append(cause.getMessage());
				log.error("transferMoney :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("transferMoney :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
}
