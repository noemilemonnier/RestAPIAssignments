package com.AnimalShelter.auth; 
 
import java.security.Principal; 
 
import javax.ws.rs.core.SecurityContext; 
 
import com.AnimalShelter.Models.User; 
 
public class AnimalShelterSecurityContext implements SecurityContext { 
	 
	private User user; 
	private boolean secure; 
	 
	public AnimalShelterSecurityContext(User user, boolean secure){ 
		this.user = user; 
		this.secure = secure; 
	} 
 
	@Override 
	public String getAuthenticationScheme() { 
		return SecurityContext.FORM_AUTH; 
	} 
 
	@Override 
	public Principal getUserPrincipal() { 
		return user; 
	} 
 
	@Override 
	public boolean isSecure() { 
		return secure;	 
	} 
 
	@Override 
	public boolean isUserInRole(String role) { 
		if (user.getRole() != null) { 
			System.out.println("isUserInRole: " + user.getRole().contains(role));
            return user.getRole().contains(role); 
        } 
        return false; 
	} 
 
}