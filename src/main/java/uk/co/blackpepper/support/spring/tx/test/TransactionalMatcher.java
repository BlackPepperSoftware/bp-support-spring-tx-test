/*
 * Copyright 2014 Black Pepper Software
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
package uk.co.blackpepper.support.spring.tx.test;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.transaction.annotation.Transactional;

public final class TransactionalMatcher extends TypeSafeMatcher<Transactional> {

	private final boolean readOnly;
	
	private TransactionalMatcher(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public static TransactionalMatcher readOnly() {
		return new TransactionalMatcher(true);
	}
	
	public static TransactionalMatcher readWrite() {
		return new TransactionalMatcher(false);
	}
	
	@Override
	public void describeTo(Description description) {
		description.appendText("transaction to be marked " + (readOnly ? "read-only" : "read-write"));
	}

	@Override
	protected boolean matchesSafely(Transactional item) {
		return readOnly == item.readOnly();
	}

	public boolean isReadOnly() {
		return readOnly;
	}
}
