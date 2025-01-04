package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.User;
import org.springframework.stereotype.Service;



public  interface UserService  {

    User findUserProfileByJwt(String jwt) throws Exception;


    User findUserByEmail(String email) throws Exception;

    User findUserById(Long  id) throws Exception;


    User updateUsersProjectSize(User user , int number) throws Exception;


}
