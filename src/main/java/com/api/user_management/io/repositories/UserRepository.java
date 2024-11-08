package com.api.user_management.io.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.user_management.io.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email); 
	
	//Find By User 
    @Query("SELECT COUNT(u) FROM users u WHERE u.userStatus = 'Active'")
    long countActiveUsers();
	UserEntity findByUserId(String userId);
	UserEntity findByEmailAndEncryptedPassword(String email, String encryptedPassword);
	Page<UserEntity> findByFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrPhoneNumberContainingOrEmailContainingOrUserStatusContaining(
			String searchKey, String searchKey2, String searchKey3, String searchKey4, String searchKey5,
			String searchKey6, Pageable pageableRequest);
	UserEntity findByEmailVerificationToken(String emailVerificationToken);
	UserEntity findByEmailAndPasswordResetCode(String email, String resetCode);
	Page<UserEntity> findByUserTypeNot(String string, Pageable pageableRequest);
	Page<UserEntity> findByUserType(String string, Pageable pageableRequest);
	
	@Query(value = "SELECT u.firstName from users where u.firstName like %:firstName%",nativeQuery = true)
	public Page<Object[]> getUsers(@Param("firstName") String  firstName, Pageable pageableRequest);

	Page<UserEntity> findByFirstNameContainingAndMiddleNameContaining(String firstName, String middleName,
			Pageable pageableRequest);

	Page<UserEntity> findByFirstNameContainingAndMiddleNameContainingAndLastNameContaining(String firstName,
			String middleName, String lastName, Pageable pageableRequest);

	Page<UserEntity> findByFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrPhoneNumberContainingOrEmailContainingAndUserStatus(
			String searchKey, String searchKey2, String searchKey3, String searchKey4, String searchKey5, String string,
			Pageable pageableRequest);

	Page<UserEntity> findByFirstNameContainingAndMiddleNameContainingAndUserStatus(String firstName, String middleName,
			String string, Pageable pageableRequest);

	Page<UserEntity> findByFirstNameContainingAndMiddleNameContainingAndLastNameContainingAndUserStatus(
			String firstName, String middleName, String lastName, String string, Pageable pageableRequest);

	Page<UserEntity> findByUserStatus(String string, Pageable pageableRequest);

	Page<UserEntity> findByUserStatusAndFirstNameContainingOrLastNameContainingOrMiddleNameContainingOrPhoneNumberContainingOrEmailContaining(
			String string, String searchKey, String searchKey2, String searchKey3, String searchKey4, String searchKey5,
			Pageable pageableRequest);

	Page<UserEntity> findByUserStatusAndFirstNameContainingOrUserStatusAndLastNameContainingOrUserStatusAndMiddleNameContainingOrUserStatusAndPhoneNumberContainingOrUserStatusAndEmailContaining(
			String string, String searchKey, String string2, String searchKey2, String string3, String searchKey3,
			String string4, String searchKey4, String string5, String searchKey5, Pageable pageableRequest);

    @Query("SELECT u FROM users u WHERE u.userStatus = :status AND " +
            "(u.firstName LIKE %:keyword% OR u.middleName LIKE %:keyword% OR u.lastName LIKE %:keyword% OR " +
            "u.phoneNumber LIKE %:keyword% OR u.email LIKE %:keyword%)")
     Page<UserEntity> searchActiveUsers(@Param("status") String status, @Param("keyword") String keyword, Pageable pageable);
}