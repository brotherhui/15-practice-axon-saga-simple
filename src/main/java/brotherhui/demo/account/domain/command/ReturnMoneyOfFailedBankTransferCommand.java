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

package brotherhui.demo.account.domain.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class ReturnMoneyOfFailedBankTransferCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    private long amount;
	public String getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public ReturnMoneyOfFailedBankTransferCommand(String bankAccountId, long amount) {
		super();
		this.bankAccountId = bankAccountId;
		this.amount = amount;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
    
    
}