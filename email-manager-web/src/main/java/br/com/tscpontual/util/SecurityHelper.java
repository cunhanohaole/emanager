package br.com.tscpontual.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityHelper {

	public static String getUserName() {
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = null;
		if (obj instanceof UserDetails) {
			username = ((UserDetails) obj).getUsername();
		} else {
			username = obj.toString();
		}
		return username;
	}

}
