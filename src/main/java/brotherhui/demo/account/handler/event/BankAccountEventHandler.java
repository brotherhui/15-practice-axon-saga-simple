/*
 * Copyright (c) 2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package brotherhui.demo.account.handler.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import brotherhui.demo.account.domain.entity.BankAccountEntry;
import brotherhui.demo.account.domain.event.BankAccountCreatedEvent;
import brotherhui.demo.account.domain.event.MoneyAddedEvent;
import brotherhui.demo.account.domain.event.MoneySubtractedEvent;
import brotherhui.demo.account.domain.repository.BankAccountRepository;

/**
 * @author xiaohui.c.liu
 * this is used to store the event result, not the event
 *
 */
@Component
public class BankAccountEventHandler {

    private BankAccountRepository repository;
    
    @Autowired
    public BankAccountEventHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(BankAccountCreatedEvent event) {
        repository.save(new BankAccountEntry(event.getId(), 0, event.getOverdraftLimit()));
    }

    @EventHandler
    public void on(MoneyAddedEvent event) {
        BankAccountEntry bankAccountEntry = repository.findOneByAxonBankAccountId(event.getBankAccountId());
        bankAccountEntry.setBalance(bankAccountEntry.getBalance() + event.getAmount());

        repository.save(bankAccountEntry);
    }

    @EventHandler
    public void on(MoneySubtractedEvent event) {
        BankAccountEntry bankAccountEntry = repository.findOneByAxonBankAccountId(event.getBankAccountId());
        bankAccountEntry.setBalance(bankAccountEntry.getBalance() - event.getAmount());

        repository.save(bankAccountEntry);
    }

}
