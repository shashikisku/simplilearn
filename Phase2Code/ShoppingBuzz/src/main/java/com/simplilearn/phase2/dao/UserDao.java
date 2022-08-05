package com.simplilearn.phase2.dao;

import com.simplilearn.phase2.dto.User;

public interface UserDao {
	public boolean validateUser(User user);
	public boolean registerUser(User user);
}
