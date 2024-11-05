package com.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.security.JwtTokenProvider;

@Component
public class RequestInterceptor implements HandlerInterceptor {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	   String redirectUrl = "/account/user_is_deactivated";
		if (redirectUrl.equals(request.getRequestURI()) || request.getRequestURI().equals("/auth/login"))
		   return true;
	   
		System.out.print(request.getRequestURI());
		String token = "";
		String authorization=request.getHeader("Authorization");
		if (authorization!=null && authorization.split(" ").length > 1)
			token = request.getHeader("Authorization").split(" ")[1];
			
		if(!"".equals(token)) {
		   Long userId = jwtTokenProvider.getUserIdFromJWT(token);
	
		   Optional<UserEntity> currentUserOptional = userRepository.findById(userId);
		   UserEntity currentUser = currentUserOptional.get();
			/*
		   if (currentUser == null)
				throw new AppException("No user found!");
			*/
			
			if (currentUser != null && !"Active".equalsIgnoreCase(currentUser.getUserStatus())) {
				System.out.println("User is deactivated 1 URI:"+request.getRequestURI());
				// throw new AppException("User is deactivated");
	
				   HttpSession session = request.getSession();
				   // User user = (User) session.getAttribute("user");
				    try{
				        if(session != null){
				     	   System.out.println("Trying to redirect to: "+redirectUrl);
				            response.sendRedirect(redirectUrl);
				            return false;
				        }
				    }catch(Exception e){
				        e.toString();
				    }
				   
			}
		}
		
      return true;
   }
   @Override
   public void postHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler, 
      ModelAndView modelAndView) throws Exception {
	   
	   System.out.println("postHandle");

   }
   
   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
      Object handler, Exception exception) throws Exception {
	      System.out.println("afterCompletion");

   }
   
   public String redirectTo() {
	    return "redirect:/user_is_deactivated";
   }
}