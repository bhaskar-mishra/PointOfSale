package com.increff.pos.dto;


import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDto {

    @Autowired
    private UserService userService;

    public void addUser(UserForm userForm) throws ApiException{
        validateUserForm(userForm);
        normalizeUserForm(userForm);
        UserPojo p = convertUserFormToPojo(userForm);
        userService.add(p);
    }

    public void deleteUser(int id) throws ApiException{
        UserPojo userPojo = userService.getById(id);
        UserPrincipal userPrincipal = SecurityUtil.getPrincipal();
        if(userPojo.getEmail().equals(userPrincipal.getEmail())){
            throw new ApiException("deletion not permitted");
        }
        userService.delete(id);
    }

    public List<UserData> getAllUser() throws ApiException{
        List<UserPojo> userPojoList = userService.getAll();
        List<UserData> userDataList = new ArrayList<UserData>();
        for (UserPojo userPojo : userPojoList) {
            userDataList.add(convertUserPojoToData(userPojo));
        }
        return userDataList;
    }

    private static UserData convertUserPojoToData(UserPojo userPojo){
        UserData userData = new UserData();
        userData.setEmail(userPojo.getEmail());
        userData.setRole(userPojo.getRole());
        userData.setId(userPojo.getId());
        return userData;
    }

    private static UserPojo convertUserFormToPojo(UserForm userForm){
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail(userForm.getEmail());
        userPojo.setRole(userForm.getRole());
        userPojo.setPassword(userForm.getPassword());
        return userPojo;
    }

    private static void normalizeUserForm(UserForm userForm) {
        userForm.setEmail(userForm.getEmail().toLowerCase().trim());
        userForm.setRole(userForm.getRole().toLowerCase().trim());
    }

    private static void validateUserForm(UserForm userForm) throws ApiException{
        if(userForm.getEmail()==null || userForm.getEmail().trim().equals("")){
            throw new ApiException("invalid email");
        }

        if(userForm.getPassword()==null || userForm.getPassword().trim().equals("")){
            throw new ApiException("invalid password");
        }
    }
}
