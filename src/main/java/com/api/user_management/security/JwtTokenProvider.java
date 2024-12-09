package com.api.user_management.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.PrivilegeRepository;
import com.api.user_management.io.repositories.RolePrivilegeRepository;
import com.api.user_management.io.repositories.RoleRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.io.repositories.UserRolesRepository;
import com.api.user_management.ui.model.response.JwtAuthenticationResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;
    
    @Value("${app.jwtPrefix}")
    private String jwtPrefix;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    RolePrivilegeRepository rolePrivilegeRepository;
    
    @Autowired
    PrivilegeRepository privilegeRepository;
    
    @Autowired
    UserRolesRepository userRolesRepository;
    
    @Autowired
    UserRepository userRepository;
    	
    public JwtAuthenticationResponse generateToken(Authentication authentication) {
    	
    	JwtAuthenticationResponse returnValue= new JwtAuthenticationResponse();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        String jwt =jwtPrefix + Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
        returnValue.setAccessToken(jwt);
        returnValue.setUserId(userPrincipal.getUsername());
        returnValue.setUserType(userPrincipal.getUserType());
        returnValue.setUserStatus(userPrincipal.getUserStatus());
        
        Optional<UserEntity> userEntity = userRepository.findById(userPrincipal.getId());
        returnValue.setFirstName(userEntity.get().getFirstName());
        returnValue.setMiddleName(userEntity.get().getMiddleName());
        returnValue.setLastName(userEntity.get().getLastName());
        returnValue.setEmail(userEntity.get().getEmail());
        returnValue.setPhoneNumber(userEntity.get().getPhoneNumber());
        returnValue.setDepartment(userEntity.get().getDepartment());
//        List<RoleResponseForLogin> roleResponses = new ArrayList<>();
//        
//        List<UserRolesEntity> userRolesEntities = userRolesRepository.findByUserId(userPrincipal.getId());
//		for(UserRolesEntity userRolesEntity: userRolesEntities) {
//			RoleResponseForLogin roleResponse = new RoleResponseForLogin();
//			RoleEntity roleEntity = roleRepository.findByIdAndIsDeleted(userRolesEntity.getRoleId(),false);
//
//			if(roleEntity!=null) {
//				roleResponse.setId(roleEntity.getId());
//				roleResponse.setRoleName(roleEntity.getRoleName());
//				roleResponse.setRoleFullName(roleEntity.getRoleFullName());
//				
//				
//				List<PrivilegeResponseModel> privilegeResponseModels = new ArrayList<>();
//				List<RolePrivilegeEntity> rolePrivilegeEntities = rolePrivilegeRepository.findByRoleIdAndIsDeleted(roleEntity.getId(),false);
//				for(RolePrivilegeEntity rolePrivilegeEntity: rolePrivilegeEntities) {
//					
//					PrivilegeResponseModel privilegeResponseModel = new PrivilegeResponseModel();
//					PrivilegeEntity privilegeEntity = privilegeRepository.findByPrivilegeIdAndIsDeleted(rolePrivilegeEntity.getPrivilegeId(),false);
//					BeanUtils.copyProperties(privilegeEntity, privilegeResponseModel);
//					privilegeResponseModels.add(privilegeResponseModel);
//					roleResponse.setPrivileges(privilegeResponseModels);
//					
//				}
//				roleResponses.add(roleResponse);
//			}
//						
//		}
//		returnValue.setRoles(roleResponses);
        
        
        
        return returnValue;
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        if(claims == null)
        	return null;
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
