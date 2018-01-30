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
@RequestMapping("/account")
public class AccountController {

	private final static Logger log = LoggerFactory.getLogger(AccountController.class);
	@Autowired
	private CommandBus commandBus;
	
	@GetMapping("/create/{accountId}/{overdraftLimit}")
	public String createAccount(@PathVariable("accountId") String accountId, @PathVariable("overdraftLimit") Long overdraftLimit) {
		final StringBuilder resultBuilder = new StringBuilder();
		commandBus.dispatch(asCommandMessage(new CreateBankAccountCommand(accountId, overdraftLimit)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("account created successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("account creation failed").append(" :: ").append(cause.getMessage());
				log.error("createAccount :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("createAccount :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
	@GetMapping("/withdraw/{accountId}/{amount}")
	public String withdrawMoney(@PathVariable("accountId") String accountId, @PathVariable("amount") Long amount) {
		final StringBuilder resultBuilder = new StringBuilder();
		commandBus.dispatch(asCommandMessage(new WithdrawMoneyCommand(accountId,amount)), new CommandCallback<Object, Object>() {
			@Override
			public void onSuccess(CommandMessage<? extends Object> commandMessage, Object result) {
				resultBuilder.append("money withdrawn successfully");
			}

			@Override
			public void onFailure(CommandMessage<? extends Object> commandMessage, Throwable cause) {
				resultBuilder.append("money withdrawal failed").append(" :: ").append(cause.getMessage());
				log.error("withdrawMoney :: {}" ,resultBuilder.toString(), cause);
			}
		});
		log.info("withdrawMoney :: {}" ,resultBuilder.toString());
		return resultBuilder.toString();
	}
	
}
