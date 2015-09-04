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
