package br.com.tscpontual.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tscpontual.email.manager.EmailManagerImpl;
import br.com.tscpontual.exception.TechnicalException;

public class EmailManagerTest extends SpringTestCase {

	@Autowired
	private EmailManagerImpl emailManagerImpl;
	
	@Test
	public void test() throws TechnicalException {
		emailManagerImpl.checkInvalidSentEmails();
	}

}
