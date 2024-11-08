package com.api.user_management.service.Impl;
import java.io.File;
import java.io.IOException;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api.exception.AppException;
import com.api.user_management.io.entity.DepartmentEntity;
import com.api.user_management.io.entity.RoleEntity;
import com.api.user_management.io.entity.UserEntity;
import com.api.user_management.io.repositories.DepartmentRepository;
import com.api.user_management.io.repositories.RoleRepository;
import com.api.user_management.io.repositories.UserRepository;
import com.api.user_management.service.UserService;
import com.api.user_management.shared.GenerateRandomString;
import com.api.user_management.shared.SendMail;
import com.api.user_management.shared.dto.UserDto;
import com.api.user_management.shared.dto.PhonebookExistsException;
import com.api.user_management.shared.dto.PhonebookInternalServerException;
import com.api.user_management.shared.dto.PhonebookNotFoundException;
import com.api.user_management.shared.dto.PhonebookSuccessfulException;
import com.api.user_management.ui.model.request.EmailVerificationRequestModel;
import com.api.user_management.ui.model.request.ResetPasswordRequestModel;
import com.api.user_management.ui.model.request.SendEmailRequestModel;
import com.api.user_management.ui.model.response.DepartmentResponseModel;

import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GenerateRandomString generateRandomString;
	
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    RoleRepository roleRepository;
	
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	DepartmentRepository departmentRepository;
	
	@Value("${file.upload-dir}")
    private String uploadDirectory;
	
	@Value("${app.HostDomain}")
    private String applicationHostDomain;
//	
//    private static final String EMAIL_PATTERN = "^[a-zA-Z]+@([a-zA-Z]+\\\\.)?unicali\\.it$";
//    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z]+@([a-zA-Z]+\\.)?unical\\.it$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    
    private static final String PHONE_NUMBER_REGEX = "^\\+?[0-9]{10,15}$";
    private static final Pattern pattern1 = Pattern.compile(PHONE_NUMBER_REGEX);

    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        Matcher matcher = pattern1.matcher(phoneNumber);
        return matcher.matches();
    }
	public UserDto createUser(UserDto user) throws AddressException, MessagingException, IOException {
		
		//UserEntity checkEmailEntity = userRepository.findByEmail(user.getEmail());
		if(userRepository.findByEmail(user.getEmail()) != null) throw new 
		PhonebookExistsException("Record already exists with this Email.");
				
//		if(!isValidEmail(user.getEmail())) throw new AppException("Email Invalid");
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
						 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.print("==========is publiccccccccccc================"+authentication.getName()+"===========================");
      
        if(authentication.getName().equals("anonymousUser")) {
        	if(user.getUserType().equals("Admin")) throw new PhonebookInternalServerException("Guest can't be registered as an admin");
        }
        else {
        	userEntity.setUserVerified(true);
        }
        
//        if (!isValidPhoneNumber(user.getPhoneNumber())) {
//            throw new AppException("Invalid phone number format. It should be between 10 and 15 digits and may start with a '+'");
//        }
		String defaultUserStatus = "NotVerified";
		String emailVerificationToken = generateRandomString.generateUserId(45);
		
		userEntity.setEmailVerificationToken(emailVerificationToken);
		userEntity.setUserStatus(defaultUserStatus);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(generateRandomString.generateUserId(30));
		
		RoleEntity userRole = roleRepository.findByRoleName(user.getUserType());
		
		if(userRole == null) 
			throw new PhonebookNotFoundException("Role not set.");
		Random random = new Random();

        int code = 100000 + random.nextInt(900000); // generates a number between 100000 and 999999
		userEntity.setVerificationCode(code);
		userEntity.setRoles(Collections.singleton(userRole));
		UserEntity storedUserDetailsEntity = userRepository.save(userEntity);
		
		String mailSubject = "Email Verification";
		String mailBody ="Dear "+ userEntity.getFirstName()+" "+userEntity.getMiddleName()+" "
		+userEntity.getLastName() +", Use this code to verify your email "+ code;
		SendEmailRequestModel sendMail = new SendEmailRequestModel();
		sendMail.setToAddress(storedUserDetailsEntity.getEmail());
		sendMail.setSubject(mailSubject);
		sendMail.setBody(mailBody);
		try {
			String returnValue1 = sendMailComponent.sendMail(sendMail);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(storedUserDetailsEntity, returnValue);
		
		return returnValue;
		
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getuser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new PhonebookNotFoundException("User not found");
		
		UserDto returnValue = new UserDto();
		DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(userEntity.getDepartmentId(),false);
		if(departmentEntity!=null) {
			BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
			returnValue.setDepartmentResponseModel(departmentResponseModel);	
		}
		BeanUtils.copyProperties(userEntity, returnValue); 
		return returnValue;
	}

	@Override
	public UserDto getUserByUserId(String UserId) {
			
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(UserId);
		
		if(userEntity == null) throw new PhonebookNotFoundException("User not found");
		
		BeanUtils.copyProperties(userEntity, returnValue); 
		DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
		DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(userEntity.getDepartmentId(),false);
		if(departmentEntity!=null) {
			BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
			returnValue.setDepartmentResponseModel(departmentResponseModel);	
		}

		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new PhonebookNotFoundException("User not found.");
		
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setMiddleName(userDto.getMiddleName());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setPhoneNumber(userDto.getPhoneNumber());
		userEntity.setUserType(userDto.getUserType());
		userEntity.setDepartment(userDto.getDepartment());
		userEntity.setAddress(userDto.getAddress());
		
		
		//updated user
//		BeanUtils.copyProperties(userDto, userEntity);
//		RoleEntity userRole = roleRepository.findByRoleName("Admin");
//		if(userRole == null) 
//			throw new AppException("User RoleEntity not set.");
//		
//		userEntity.setRoles(Collections.singleton(userRole));
		
				
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		
//		RoleEntity roleEntity = roleRepository.findByRoleName(userDto.getUserType());
//		if(roleEntity!= null) {		
//			long Id = userEntity.getId();
//			long roleId = roleEntity.getId();
//			
//			user
//			entityManager.createQuery("UPDATE user_roles set role_id =:roleId WHERE user_id=:Id")
//		     .setParameter("roleId", roleId)
//		     .setParameter("Id", Id)
//		     .executeUpdate();	
		
		
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue); 
		return returnValue;
	}
	
	@Override
	public UserDto updateUserStatus(String userId, UserDto userDto) {
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new PhonebookNotFoundException("User not found.");
		
		userEntity.setUserStatus(userDto.getUserStatus());		
		UserEntity updatesUserDetailsEntity = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatesUserDetailsEntity, returnValue); 
		return returnValue;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) 
			throw new PhonebookNotFoundException("User not found");
		
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDto> getUsers(int page, int limit, String userType, String searchKey, boolean isPublic, String department, String role) throws IOException{
		 
	    List<UserDto> returnValue = new ArrayList<>();
	    
	    if(page > 0) page = page - 1; 
	   
	    Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("id").descending());
	    Page<UserEntity> usersPage = null;
	    
	    int countSpaces = StringUtils.countOccurrencesOf(searchKey, " ");
	    long totalUsers = userRepository.countActiveUsers();
	    
//	    long totalUsers =userRepository.countByStatus("Active");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        searchKey = searchKey.trim();
	    String[] searchKeyArray = searchKey.split(" ");


	    ///chick if it's public or not
	    if(isPublic) {
	    	if(searchKey.length()<=2) {
	    		usersPage = userRepository.findByUserStatus("Active", pageableRequest);
	    	}
	    	else if(countSpaces == 0) {
		    	System.out.print("=====ooooooo=length=1=======rrrrrrrrrrrrrrrrrrrrr===is publicsearchkey==="+searchKey.length()+"=====yyyyyyyy=========");

		    	usersPage = userRepository.searchActiveUsers("Active",searchKey,pageableRequest);
//	    	usersPage = userRepository.findByUserStatusAndFirstNameContainingOrUserStatusAndLastNameContainingOrUserStatusAndMiddleNameContainingOrUserStatusAndPhoneNumberContainingOrUserStatusAndEmailContaining("Active", searchKey,"Active",searchKey,"Active",searchKey,"Active",searchKey,"Active",searchKey,pageableRequest);
	    }
	    else if(countSpaces == 1){

	    	String firstName = searchKeyArray[0];
	    	String middleName = searchKeyArray[1];

	    	usersPage = userRepository.findByFirstNameContainingAndMiddleNameContainingAndUserStatus(firstName,middleName,"Active",pageableRequest);
	    }
	    else if(countSpaces == 2) {
	    	String firstName = searchKeyArray[0];
	    	String middleName = searchKeyArray[1];
	    	String lastName = searchKeyArray[2];
	    	usersPage = userRepository.findByFirstNameContainingAndMiddleNameContainingAndLastNameContainingAndUserStatus(firstName,middleName,lastName,"Active",pageableRequest);
	    }
	    
	    }
	    
	    else {

	    	if(searchKey.length()<=2) {

	    		usersPage = userRepository.findAll(pageableRequest);
	    	}
	    	else
	    		if(countSpaces == 0) {
	    	usersPage = userRepository.findByFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrPhoneNumberContainingOrEmailContainingOrUserStatusContaining(searchKey,searchKey,searchKey,searchKey,searchKey,searchKey,pageableRequest);
	    }
	    else if(countSpaces == 1){
	    	String firstName = searchKeyArray[0];
	    	String middleName = searchKeyArray[1];
	    	usersPage = userRepository.findByFirstNameContainingAndMiddleNameContaining(firstName,middleName,pageableRequest);
	    }
	    else if(countSpaces == 2) {
	    	String firstName = searchKeyArray[0];
	    	String middleName = searchKeyArray[1];
	    	String lastName = searchKeyArray[2];
	    	usersPage = userRepository.findByFirstNameContainingAndMiddleNameContainingAndLastNameContaining(firstName,middleName,lastName,pageableRequest);
	    }
	    
	    }
//	    if(userType.equals("All")) {
//	    	usersPage = userRepository.findAll(pageableRequest);
//	    }else {
//	    	usersPage = userRepository.findByUserType(userType,pageableRequest);
//	    }
	   
	    
	    int totalPages = usersPage.getTotalPages();
	    List<UserEntity> users = usersPage.getContent();
	    
	    if(!department.equals("")) {
	    users = users.stream()
                .filter(user -> department.equals(user.getDepartment()))
                .collect(Collectors.toList());
	    }
	    if(!role.equals("")) {
	    	
	    users = users.stream()
                .filter(user -> role.equals(user.getUserType()))
                .collect(Collectors.toList());
	    }
	    for(UserEntity userEntity : users) {
	    	UserDto userDto = new UserDto(); 
	    	BeanUtils.copyProperties(userEntity, userDto);
			DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
			DepartmentEntity departmentEntity = departmentRepository.findByDepartmentIdAndIsDeleted(1,
					false);

			if(departmentEntity!=null) {
				BeanUtils.copyProperties(departmentEntity, departmentResponseModel);
				userDto.setDepartmentResponseModel(departmentResponseModel);	
			}

	    	if(returnValue.size() == 0) {
	    		userDto.setTotalUsers(totalUsers);
	    		userDto.setTotalPages(totalPages);
	    		
	    	}
	    	
	    	returnValue.add(userDto);
	    }
	    
		return returnValue;
	}

	@Override
	public String checkEmail(String email) {
		if(userRepository.findByEmail(email) == null) {
			return "Email doesn't exist";
		}else {
			return "Email exists";
		}
	}

	@Override
	public String resetPassword(ResetPasswordRequestModel resetPasswordDetail) {
		
		String returnValue = "Password not changed";
		UserEntity userEntity = userRepository.findByEmailAndPasswordResetCode(resetPasswordDetail.getEmail(),resetPasswordDetail.getResetCode());
		if(userEntity == null) throw new PhonebookInternalServerException("Password Can't be changed");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(resetPasswordDetail.getNewPassword()));
		UserEntity passworUpdated = userRepository.save(userEntity);
		if(passworUpdated != null) {
			returnValue = "Password changed successfully";
		}
		throw new PhonebookSuccessfulException("Password changed successfully");

//		return returnValue;
	}

	@Override
	public String changeAccountPassword(ResetPasswordRequestModel changePassRequest) {
		
		UserEntity userEntity = userRepository.findByEmail(changePassRequest.getEmail());
		
		if(userEntity == null) 
			throw new PhonebookNotFoundException("User not found.");
		
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(changePassRequest.getNewPassword()));
		userRepository.save(userEntity);
		throw new PhonebookSuccessfulException("Password changed successfully");

//		return "Password changed successfully";
	}
	@Override
	public String verifyEmail(EmailVerificationRequestModel requestModel) {
		// TODO Auto-generated method stub
		String returnValue = new String(); 
		if(requestModel.getVerificationCode()==null) {
			throw new PhonebookNotFoundException("Code Not Sent");
		}
		UserEntity userEntity = userRepository.findByEmail(requestModel.getEmail());
		
		if(userEntity==null) throw new PhonebookNotFoundException("User not found");
		
		if(requestModel.getVerificationCode().equals(userEntity.getVerificationCode())) {
			userEntity.setUserVerified(true);


			userEntity.setUserStatus("Active");
			userRepository.save(userEntity);
			System.out.print("==============Brhane========="+requestModel.getEmail()+"===========");
			System.out.print("==============email========="+userEntity.getEmail()+"===========");

			returnValue = "Email Verified Successfully";
		}
		else {
			throw new PhonebookInternalServerException("Email Not Verified");

//			returnValue = "Email Not Verified";
		}
		return returnValue;
	}
	
	@Autowired
	SendMail sendMailComponent;

    
	public String resendCode(EmailVerificationRequestModel requestModel) {
		// TODO Auto-generated method stub
		String returnValue = new String();
		
		UserEntity userEntity = userRepository.findByEmail(requestModel.getEmail());
		if(userEntity==null) throw new PhonebookNotFoundException("User not found");
		System.out.print("=======resennddddd======="+requestModel.getEmail().length()+"=====================");

		Random random = new Random();
        int code = 100000 + random.nextInt(900000); // generates a number between 100000 and 999999
		userEntity.setVerificationCode(code);
		userEntity.setUserVerified(false);
		userRepository.save(userEntity);

		String mailSubject = "Email Verification";
		String mailBody ="Dear "+ userEntity.getFirstName()+" "+userEntity.getMiddleName()+" "+userEntity.getLastName() +", Use this code to verify your email "+ code;
		SendEmailRequestModel sendMail = new SendEmailRequestModel();
		sendMail.setToAddress(requestModel.getEmail());
		sendMail.setSubject(mailSubject);
		sendMail.setBody(mailBody);
		try {
			String returnValue1 = sendMailComponent.sendMail(sendMail);
		} catch (MessagingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new PhonebookSuccessfulException("Code Successfully Sent");
//		return "Code Successfully Sent";
	}

	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("bretama99@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		
		System.out.print("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
		
	}

}
