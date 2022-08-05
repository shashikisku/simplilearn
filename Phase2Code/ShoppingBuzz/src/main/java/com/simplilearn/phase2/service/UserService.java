package com.simplilearn.phase2.service;

import com.simplilearn.phase2.dto.User;

public interface UserService {
	public boolean validateUser(User user);
	public boolean registerUser(User user);

}
