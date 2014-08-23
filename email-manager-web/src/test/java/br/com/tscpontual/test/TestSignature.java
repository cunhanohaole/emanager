package br.com.tscpontual.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tscpontual.user.manager.UserManager;
import br.com.tscpontual.user.model.Signature;

public class TestSignature extends SpringTestCase {

	@Autowired
	private UserManager userManager;
	
	@Test
	@Ignore
	public void shouldListSignatures() {
		String username = "marcelo";
		Signature signature = userManager.newSignature(username, "Test1", "<h4>Fernando</h4>");
		Assert.assertTrue(signature != null && signature.getIdSignature() != null);
		List<Signature> signatures = userManager.listSignatures(username);
		Assert.assertTrue(CollectionUtils.isNotEmpty(signatures));
	}
	
}