package com.xmall.dao;

import com.xmall.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password") String md5Password);

    int checkEmail(String email);

    String selectQuestionByUsername(String username);

    int selectCheckAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

    int checkPassword(@Param("password") String password, @Param("id") int id);

    int checkEmailByUserId(@Param("email") String email, @Param("id") int id);

    int checkAdminRole(User user);

}