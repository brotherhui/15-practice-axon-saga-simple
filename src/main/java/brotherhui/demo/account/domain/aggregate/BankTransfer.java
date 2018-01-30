package brotherhui.demo.account.domain.aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;

import brotherhui.demo.account.domain.command.CreateBankTransferCommand;
import brotherhui.demo.account.domain.command.MarkBankTransferCompletedCommand;
import brotherhui.demo.account.domain.command.MarkBankTransferFailedCommand;
import brotherhui.demo.account.domain.event.BankTransferCompletedEvent;
import brotherhui.demo.account.domain.event.BankTransferCreatedEvent;
import brotherhui.demo.account.domain.event.BankTransferFailedEvent;

@Aggregate
public class BankTransfer {

    @AggregateIdentifier
    private String bankTransferId;
    private String sourceBankAccountId;
    private String destinationBankAccountId;
    private long amount;
    private Status status;

    @SuppressWarnings("unused")
    protected BankTransfer() {
    }

    @CommandHandler
    public BankTransfer(CreateBankTransferCommand command) {
        apply(new BankTransferCreatedEvent(command.getBankTransferId(),
                                           command.getSourceBankAccountId(),
                                           command.getDestinationBankAccountId(),
                                           command.getAmount()));
    }

    @CommandHandler
    public void handle(MarkBankTransferCompletedCommand command) {
        apply(new BankTransferCompletedEvent(command.getBankTransferId()));
    }

    @CommandHandler
    public void handle(MarkBankTransferFailedCommand command) {
        apply(new BankTransferFailedEvent(command.getBankTransferId()));
    }

    @EventHandler
    public void on(BankTransferCreatedEvent event) throws Exception {
        this.bankTransferId = event.getBankTransferId();
        this.sourceBankAccountId = event.getSourceBankAccountId();
        this.destinationBankAccountId = event.getDestinationBankAccountId();
        this.amount = event.getAmount();
        this.status = Status.STARTED;
    }

    @EventHandler
    public void on(BankTransferCompletedEvent event) {
        this.status = Status.COMPLETED;
    }

    @EventHandler
    public void on(BankTransferFailedEvent event) {
        this.status = Status.FAILED;
    }

    private enum Status {
        STARTED,
        FAILED,
        COMPLETED
    }
}