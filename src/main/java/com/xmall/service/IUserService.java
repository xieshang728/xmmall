package com.xmall.service;


import com.xmall.common.RestResponse;
import com.xmall.entity.User;

/**
 * @author xies
 * @date 2018/1/21
 */
public interface IUserService {

    RestResponse<User> login(String username, String password);

    RestResponse<String> register(User user);

    RestResponse<String> checkValid(String str, String type);

    RestResponse<String> selectQuestion(String username);

    RestResponse<String> selectCheckAnswer(String username, String question, String answer);

    RestResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken);

    RestResponse<String> resetPassword(String oldPassword, String newPassword, User user);

    RestResponse<User> updateInformation(User user);

    RestResponse<User> getInformation(int id);

    RestResponse<String> checkAdminRole(User user);

}
