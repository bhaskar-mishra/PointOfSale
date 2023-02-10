package com.increff.pos.dto;

import com.increff.pos.model.data.UserData;
import com.increff.pos.model.form.UserForm;
import com.increff.pos.service.AbstractUnitTest;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest {

    @Autowired
    private UserDto userDto;


    @Test
    public void addUserTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com","aayush","admin");
        userDto.addUser(userForm);
        UserData userData = userDto.getByEmail(userForm.getEmail());
        assertEquals(userData.getEmail(),userForm.getEmail());
        assertEquals(userData.getRole(),userForm.getRole());
    }

    @Test(expected = ApiException.class)
    public void deleteUserTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com","aayush","admin");
        userDto.addUser(userForm);

        UserData userData = userDto.getByEmail(userForm.getEmail());
        userDto.deleteUser(userData.getId());

        UserData userDataPostDelete = userDto.getByEmail(userForm.getEmail());
    }

    @Test
    public void getAllUsers() throws ApiException {
        List<UserForm> userForms = Arrays.asList(new UserForm(),new UserForm());
        TestUtils.setUserForm(userForms.get(0),"aayush@maurya.com","aayush","admin");
        TestUtils.setUserForm(userForms.get(1),"saksham@yadav.com","saksham","standard");
        userDto.addUser(userForms.get(0));
        userDto.addUser(userForms.get(1));

        List<UserData> userDataList = userDto.getAllUser();

        assertEquals(userDataList.get(0).getRole(),userForms.get(0).getRole());
        assertEquals(userDataList.get(0).getEmail(),userForms.get(0).getEmail());

        assertEquals(userDataList.get(1).getEmail(),userForms.get(1).getEmail());
        assertEquals(userDataList.get(1).getRole(),userForms.get(1).getRole());
    }

    @Test
    public void getByEmailTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya","aayush","admin");
        userDto.addUser(userForm);

        UserData userData = userDto.getByEmail(userForm.getEmail());
        assertEquals(userData.getRole(),userForm.getRole());
        assertEquals(userData.getEmail(),userForm.getEmail());
    }

    @Test
    public void normalizeUserFormTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"   AaYuSh@MauRYa.CoM  ","aayush","  aDMiN  ");
        DtoUtils.normalizeUserForm(userForm);

        assertEquals("aayush@maurya.com",userForm.getEmail());
        assertEquals("admin",userForm.getRole());
    }

    @Test(expected = ApiException.class)
    public void validateUserFormNullEmailTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,null,"password","admin");
        DtoUtils.validateUserForm(userForm);
    }
    @Test(expected = ApiException.class)
    public void validateUserFormEmptyEmailTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"   ","password","admin");
        DtoUtils.validateUserForm(userForm);
    }

    @Test(expected = ApiException.class)
    public void validateUserFormNullPasswordTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com",null,"admin");
        DtoUtils.validateUserForm(userForm);
    }

    @Test(expected = ApiException.class)
    public void validateUserFormEmptyPasswordTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com","   ","admin");
        DtoUtils.validateUserForm(userForm);
    }

    @Test(expected = ApiException.class)
    public void validateUserFormNullRoleTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com","password",null);
        DtoUtils.validateUserForm(userForm);
    }

    @Test(expected = ApiException.class)
    public void validateUserFormEmptyRoleTest() throws ApiException{
        UserForm userForm = new UserForm();
        TestUtils.setUserForm(userForm,"aayush@maurya.com","password","  ");
        DtoUtils.validateUserForm(userForm);
    }




}
