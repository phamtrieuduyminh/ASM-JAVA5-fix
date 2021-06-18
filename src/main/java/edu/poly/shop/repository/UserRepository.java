package edu.poly.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.domain.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, String> {

	Account findByUsername(String username);
	Account findByUsernameAndPassword(String username , String password);
	
	
}
