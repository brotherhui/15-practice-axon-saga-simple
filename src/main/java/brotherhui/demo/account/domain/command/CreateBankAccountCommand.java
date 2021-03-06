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

import javax.validation.constraints.Min;

public class CreateBankAccountCommand {

    @TargetAggregateIdentifier
    private String bankAccountId;
    @Min(value = 0, message = "Overdraft limit must not be less than zero")
    private long overdraftLimit;
    
	public String getBankAccountId() {
		return bankAccountId;
	}
	public void setBankAccountId(String bankAccountId) {
		this.bankAccountId = bankAccountId;
	}
	public long getOverdraftLimit() {
		return overdraftLimit;
	}
	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	public CreateBankAccountCommand(String bankAccountId, long overdraftLimit) {
		super();
		this.bankAccountId = bankAccountId;
		this.overdraftLimit = overdraftLimit;
	}
	
	
    
    
}
