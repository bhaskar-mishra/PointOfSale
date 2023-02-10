package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.UserDao;
import com.increff.pos.pojo.UserPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class UserService {

	@Autowired
	private UserDao userDao;

	public void add(UserPojo userPojo) throws ApiException {
		UserPojo existing = userDao.select(userPojo.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		userDao.insert(userPojo);
	}


	public UserPojo getByEmail(String email) throws ApiException {
		UserPojo userPojo =  userDao.select(email);
		if(userPojo==null){
			throw new ApiException("invalid email");
		}

		return userPojo;
	}

	public List<UserPojo> getAll() {
		return userDao.selectAll();
	}


	public void delete(int id) throws ApiException {
		UserPojo userPojo = userDao.selectById(id);
		if(userPojo==null){
               throw new ApiException("invalid id");
		}
		userDao.delete(id);
	}

	public UserPojo getById(int id) throws ApiException{
		UserPojo userPojo = userDao.selectById(id);
		if(userPojo==null){
			throw new ApiException("invalid id");
		}

		return userPojo;
	}

}
