package springboot.spring_boot_web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import springboot.spring_boot_web.model.User;

@Repository
public class UserService implements IUserService {
	final static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Override
	public List<User> getValidUsers() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
