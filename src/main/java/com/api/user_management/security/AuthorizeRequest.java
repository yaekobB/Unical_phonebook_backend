package com.api.user_management.security;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.exception.AppException;
import com.api.user_management.io.entity.PrivilegeEntity;
import com.api.user_management.io.entity.RoleEntity;
import com.api.user_management.io.entity.RolePrivilegeEntity;
import com.api.user_management.io.repositories.PrivilegeRepository;
import com.api.user_management.io.repositories.RolePrivilegeRepository;
import com.api.user_management.io.repositories.RoleRepository;

public class AuthorizeRequest {
	
	
	@Autowired
    RoleRepository roleRepository;
    
    @Autowired
    RolePrivilegeRepository rolePrivilegeRepository;
    
    @Autowired
    PrivilegeRepository privilegeRepository;
    
    
    public String authorization() throws AddressException, MessagingException, IOException{
    	
//    	if(true) throw new AppException("Unauthorized");
    	Object principal = SecurityContextHolder. getContext(). getAuthentication(). getPrincipal();
    	System.out.print("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+((UserDetails)principal).getUsername());
		List<RoleEntity> roleEntities = (List<RoleEntity>) roleRepository.findAll();
		for(RoleEntity roleEntity: roleEntities) {
			
			List<RolePrivilegeEntity> rolePrivilegeEntities = rolePrivilegeRepository.findByRoleIdAndIsDeleted(roleEntity.getId(),false);
			for(RolePrivilegeEntity rolePrivilegeEntity: rolePrivilegeEntities) {
				PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(),false);
			}
		
			}
		return "";
    }

}
