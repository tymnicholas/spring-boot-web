package springboot.spring_boot_web.service;

import java.util.List;

import springboot.spring_boot_web.model.User;

public interface IUserService {
	List<User> getValidUsers();
}
