package com.simplilearn.phase2.service;

import com.simplilearn.phase2.dao.UserDao;
import com.simplilearn.phase2.dao.UserDaoImpl;
import com.simplilearn.phase2.dto.User;

public class UserServiceImpl implements UserService {
	private UserDao dao;

	public UserServiceImpl() {
		dao = new UserDaoImpl();
	}

	@Override
	public boolean validateUser(User user) {
		return dao.validateUser(user);
	}

	@Override
	public boolean registerUser(User user) {
		return dao.registerUser(user);
	}

}
