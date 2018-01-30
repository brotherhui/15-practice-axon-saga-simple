package brotherhui.demo.account.domain.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import brotherhui.demo.account.domain.command.CreateBankAccountCommand;
import brotherhui.demo.account.domain.command.DepositMoneyCommand;
import brotherhui.demo.account.domain.command.ReturnMoneyOfFailedBankTransferCommand;
import brotherhui.demo.account.domain.command.WithdrawMoneyCommand;
import brotherhui.demo.account.domain.event.BankAccountCreatedEvent;
import brotherhui.demo.account.domain.event.DestinationBankAccountCreditedEvent;
import brotherhui.demo.account.domain.event.MoneyAddedEvent;
import brotherhui.demo.account.domain.event.MoneyDepositedEvent;
import brotherhui.demo.account.domain.event.MoneyOfFailedBankTransferReturnedEvent;
import brotherhui.demo.account.domain.event.MoneySubtractedEvent;
import brotherhui.demo.account.domain.event.MoneyWithdrawnEvent;
import brotherhui.demo.account.domain.event.SourceBankAccountDebitRejectedEvent;
import brotherhui.demo.account.domain.event.SourceBankAccountDebitedEvent;

@Aggregate
public class BankAccount {

    @AggregateIdentifier
    private String id;
    private long overdraftLimit;
    private long balanceInCents;

    @SuppressWarnings("unused")
    private BankAccount() {
    }

    @CommandHandler
    public BankAccount(CreateBankAccountCommand command) {
        apply(new BankAccountCreatedEvent(command.getBankAccountId(), command.getOverdraftLimit()));
    }

    @CommandHandler
    public void deposit(DepositMoneyCommand command) {
        apply(new MoneyDepositedEvent(id, command.getAmountOfMoney()));
    }

    @CommandHandler
    public void withdraw(WithdrawMoneyCommand command) {
        if (command.getAmountOfMoney() <= balanceInCents + overdraftLimit) {
            apply(new MoneyWithdrawnEvent(id, command.getAmountOfMoney()));
        }
    }

    public void debit(long amount, String bankTransferId) {
        if (amount <= balanceInCents + overdraftLimit) {
            apply(new SourceBankAccountDebitedEvent(id, amount, bankTransferId));
        }
        else {
            apply(new SourceBankAccountDebitRejectedEvent(bankTransferId));
        }
    }

    public void credit(long amount, String bankTransferId) {
        apply(new DestinationBankAccountCreditedEvent(id, amount, bankTransferId));
    }

    @CommandHandler
    public void returnMoney(ReturnMoneyOfFailedBankTransferCommand command) {
        apply(new MoneyOfFailedBankTransferReturnedEvent(id, command.getAmount()));
    }

    @EventSourcingHandler
    public void on(BankAccountCreatedEvent event) {
        this.id = event.getId();
        this.overdraftLimit = event.getOverdraftLimit();
        this.balanceInCents = 0;
    }

    @EventSourcingHandler
    public void on(MoneyAddedEvent event) {
        balanceInCents += event.getAmount();
    }

    @EventSourcingHandler
    public void on(MoneySubtractedEvent event) {
        balanceInCents -= event.getAmount();
    }
}
