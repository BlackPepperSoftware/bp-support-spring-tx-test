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

import org.hamcrest.StringDescription;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static uk.co.blackpepper.support.spring.tx.test.TransactionalMatcher.readOnly;
import static uk.co.blackpepper.support.spring.tx.test.TransactionalMatcher.readWrite;

public class TransactionalMatcherTest {

	@Test
	public void readOnlyReturnsMatcher() {
		TransactionalMatcher actual = readOnly();
		
		assertThat(actual.isReadOnly(), is(true));
	}
	
	@Test
	public void readWriteReturnsMatcher() {
		TransactionalMatcher actual = readWrite();
		
		assertThat(actual.isReadOnly(), is(false));
	}
	
	@Test
	public void describeToWhenReadOnlyAppendsDescription() {
		StringDescription description = new StringDescription();
		
		readOnly().describeTo(description);
		
		assertThat(description.toString(), is("transaction to be marked read-only"));
	}
	
	@Test
	public void describeToWhenReadWriteAppendsDescription() {
		StringDescription description = new StringDescription();
		
		readWrite().describeTo(description);
		
		assertThat(description.toString(), is("transaction to be marked read-write"));
	}
	
	@Test
	public void matchesSafelyWhenReadOnlyWithReadOnlyReturnsTrue() throws NoSuchMethodException {
		assertThat(readOnly().matchesSafely(readOnlyAnnotation()), is(true));
	}
	
	@Test
	public void matchesSafelyWhenReadOnlyWithReadWriteReturnsFalse() throws NoSuchMethodException {
		assertThat(readOnly().matchesSafely(readWriteAnnotation()), is(false));
	}

	@Test
	public void matchesSafelyWhenReadWriteWithReadWriteReturnsTrue() throws NoSuchMethodException {
		assertThat(readWrite().matchesSafely(readWriteAnnotation()), is(true));
	}
	
	@Test
	public void matchesSafelyWhenReadWriteWithReadOnlyReturnsFalse() throws NoSuchMethodException {
		assertThat(readWrite().matchesSafely(readOnlyAnnotation()), is(false));
	}
	
	@Transactional(readOnly = true)
	public Transactional readOnlyAnnotation() throws NoSuchMethodException {
		return getTransactional("readOnlyAnnotation");
	}

	@Transactional(readOnly = false)
	public Transactional readWriteAnnotation() throws NoSuchMethodException {
		return getTransactional("readWriteAnnotation");
	}
	
	private Transactional getTransactional(String methodName) throws NoSuchMethodException {
		return getClass().getMethod(methodName).getAnnotation(Transactional.class);
	}
}
