package com.api.user_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.PrivilegeRepository;
import com.api.user_management.io.repositories.RolePrivilegeRepository;
import com.api.user_management.io.repositories.RoleRepository;
import com.api.user_management.io.repositories.UserRepository;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,	
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
 /*
  @Bean
  public BCryptPasswodEncoder bCryptPasswordEncoder(){
      return new BCryptPasswodEncoder();
  }
  private final UserDetailsService userDetailsService
  private final BCryptPasswordEncoder bCryptPasswordEncoder
  public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder){
   this.userDetailsService = userDetailsService
   this.bCryptPasswordEncoder = bCryptPasswordEncoder
  }
      		@Override
    	protected void configure(HttpSecurity http) throws Exception {

    		      http
    		    .authorizeRequests()
    		        .antMatchers(HttpMethod.POST, "/users" )
    		            .permitAll().anyRequest().authenticated();
    		            }
   @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
  */
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    	
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RolePrivilegeRepository rolePrivilegeRepository;
    
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Override
    protected void configure(HttpSecurity http) throws Exception {
Object object=SecurityContextHolder.getContext().getAuthentication();
    		      if(object!=null) {
    		      String userNameString=((UserDetails)object).getUsername();
    		      UserEntity userEntity = userRepository.findByEmail(userNameString);
    		      
    		      userEntity.getUserType();
    		      }
    		      http
    		        .cors()
    		        .and()
    		    .csrf()
    		        .disable()
    		    .exceptionHandling()
    		        .authenticationEntryPoint(unauthorizedHandler)
    		        .and()
    		    .sessionManagement()
    		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    		        .and()
    		    .authorizeRequests()
    		        .antMatchers("/","/favicon.ico","//*.png","//*.gif","//*.svg", "//*.jpg","//*.html", "//*.css", "//*.js", "/download/")
    		            .permitAll()
    	                .antMatchers("/ws/**").permitAll()  // Allow unauthenticated access to /ws
    		        .antMatchers(HttpMethod.POST, "/user/login","/account/resetpassword","/user/signup","/user"
    		            )
    		          .permitAll()
    		          .antMatchers(HttpMethod.PUT, "/user"
    	    		            )
    	    		          .permitAll()
    	    		          .antMatchers(HttpMethod.PUT, "/user/*"
    	    	    		            )
    	    	    		          .permitAll()
    		        .antMatchers(HttpMethod.GET, "/user"
        		            )
        		          .permitAll();
    		      
    		        http.authorizeRequests()
    		        .antMatchers(
    		            "/auth/changepassword"
				)
		        .hasAnyRole("ADMIN", "ADMINISTRATION", "FACULTY", "STUDENT")
    		        .antMatchers(
    		         "/user/*","/department/*","/roles/*"
				).hasAnyRole( "ADMIN");
    		    
    		      http.authorizeRequests()
    		      .anyRequest().authenticated();
    		     
    		        // Add our custom JWT security filter
    		        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    		    }
    		}
