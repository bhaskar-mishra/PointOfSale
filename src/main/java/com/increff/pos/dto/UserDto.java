package com.increff.pos.dto;


import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDto {

    @Autowired
    private UserService userService;

    public void addUser(UserForm userForm) throws ApiException{
        DtoUtils.validateUserForm(userForm);
        DtoUtils.normalizeUserForm(userForm);
        UserPojo p = DtoUtils.convertUserFormToPojo(userForm);
        userService.add(p);
    }

    public void deleteUser(int id) throws ApiException{
        UserPojo userPojo = userService.getById(id);
        UserPrincipal userPrincipal = SecurityUtil.getPrincipal();
        if(userPrincipal!=null && userPojo.getEmail().equals(userPrincipal.getEmail())){
            throw new ApiException("deletion not permitted");
        }
        userService.delete(id);
    }

    public List<UserData> getAllUser() throws ApiException{
        List<UserPojo> userPojoList = userService.getAll();
        List<UserData> userDataList = new ArrayList<UserData>();
        for (UserPojo userPojo : userPojoList) {
            userDataList.add(DtoUtils.convertUserPojoToData(userPojo));
        }
        return userDataList;
    }

    public UserData getByEmail(String email) throws ApiException{
        UserPojo userPojo = userService.getByEmail(email);
        return DtoUtils.convertUserPojoToData(userPojo);
    }


}
