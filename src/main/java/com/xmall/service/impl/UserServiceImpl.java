package com.xmall.service.impl;

import com.xmall.common.Const;
import com.xmall.common.RestResponse;
import com.xmall.common.TokenCache;
import com.xmall.dao.UserMapper;
import com.xmall.entity.User;
import com.xmall.service.IUserService;
import com.xmall.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xies
 * @date 2018/1/21
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    //@Autowired
    //private RedisTemplate redisTemplate;

    @Override
    public RestResponse<User> login(String username, String password) {
        RestResponse<User> response = null;
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            response = RestResponse.error("用户名不存在");
            log.error(username + "不存在");
            return response;
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            response = RestResponse.error("登录密码错误");
            log.error("登录密码错误");
            return response;
        }
        user.setPassword(Const.EMPTY);
        return RestResponse.success("登录成功", user);
    }

    @Override
    public RestResponse<String> register(User user) {
        RestResponse response = null;
        RestResponse checkUsernameResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if (!checkUsernameResponse.isSuccess()) {
            return checkUsernameResponse;
        }
        RestResponse checkEmailResponse = this.checkValid(user.getEmail(), Const.EMAIL);
        if (!checkEmailResponse.isSuccess()) {
            return checkEmailResponse;
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        user.setRole(Const.Role.ROLE_CUSTOMER);
        int resultCount = userMapper.insert(user);
        if (resultCount == 1) {
            return RestResponse.success();
        } else {
            return RestResponse.error("注册失败");
        }
    }

    @Override
    public RestResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return RestResponse.error("用户名已存在");
                }
            }

            if (Const.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return RestResponse.error("email已存在");
                }
            }
        } else {
            return RestResponse.error("参数错误");
        }
        return RestResponse.success("校验成功");
    }

    @Override
    public RestResponse<String> selectQuestion(String username) {
        RestResponse response = checkValid(username, Const.USERNAME);
        if (response.isSuccess()) {
            return RestResponse.error("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isBlank(question)) {
            return RestResponse.error("用户问题为空");
        }
        return RestResponse.success(question);
    }

    @Override
    public RestResponse<String> selectCheckAnswer(String username, String question, String answer) {
        int resultCount = userMapper.selectCheckAnswer(username, question, answer);
        if (resultCount > 0) {
            String forgetToken = UUID.randomUUID().toString();
            //redis存储
            //redisTemplate.opsForValue().set(TokenCache.TOKEN_PREFIX + username, forgetToken, 15, TimeUnit.MINUTES);
            //Google gunva TokenCache存储
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return RestResponse.success(forgetToken);
        }
        return RestResponse.error("问题的答案错误");
    }

    @Override
    public RestResponse<String> forgetResetPassword(String username, String newPassword, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            RestResponse.error("参数错误，forgetToken需要传递");
        }
        RestResponse restResponse = checkValid(username, Const.CURRENT_USER);
        if (!restResponse.isSuccess()) {
            RestResponse.error("用户不存在");
        }
        String token =TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
                //(String) redisTemplate.opsForValue().get(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return RestResponse.error("重置密码时间过期");
        }
        if (token.equals(forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
            int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (resultCount == 1) {
                return RestResponse.success();
            }
        } else {
            RestResponse.error("token错误,请重新获取");
        }
        return RestResponse.error("密码修改错误");
    }


    @Override
    public RestResponse<String> resetPassword(String oldPassword, String newPassword, User user) {
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(oldPassword), user.getId());
        if (resultCount == 0) {
            log.error("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if (resultCount > 0) {
            return RestResponse.success();
        }
        return RestResponse.error("密码更新失败");
    }

    @Override
    public RestResponse<User> updateInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return RestResponse.error("邮箱已经被其他用户使用");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return RestResponse.success("更新个人信息成功", updateUser);
        }
        return RestResponse.error("更新个人信息失败");
    }

    @Override
    public RestResponse<User> getInformation(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return RestResponse.error("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return RestResponse.success(user);
    }

    @Override
    public RestResponse<String> checkAdminRole(User user) {
        int resultCount = userMapper.checkAdminRole(user);
        if (resultCount == 1) {
            return RestResponse.success();
        }
        return RestResponse.error();
    }
}
