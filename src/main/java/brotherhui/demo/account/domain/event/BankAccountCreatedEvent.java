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

package brotherhui.demo.account.domain.event;

public class BankAccountCreatedEvent {

    private String id;
    private long overdraftLimit;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getOverdraftLimit() {
		return overdraftLimit;
	}
	public void setOverdraftLimit(long overdraftLimit) {
		this.overdraftLimit = overdraftLimit;
	}
	public BankAccountCreatedEvent(String id, long overdraftLimit) {
		super();
		this.id = id;
		this.overdraftLimit = overdraftLimit;
	}

    
    

}
