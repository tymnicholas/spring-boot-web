package springboot.spring_boot_web;

import org.springframework.batch.item.*;

import springboot.spring_boot_web.model.User;

public class UserItemProcessor implements ItemProcessor<User, User>{

	@Override
	public User process(User user) throws Exception {
		// TODO Auto-generated method stub
		return user;
	}
	
}
