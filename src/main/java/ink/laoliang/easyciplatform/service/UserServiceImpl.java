package ink.laoliang.easyciplatform.service;

import ink.laoliang.easyciplatform.domain.User;
import ink.laoliang.easyciplatform.domain.response.LoginResponse;
import ink.laoliang.easyciplatform.exception.IllegalParameterException;
import ink.laoliang.easyciplatform.repository.UserRepository;
import ink.laoliang.easyciplatform.util.MD5EncodeUtil;
import ink.laoliang.easyciplatform.util.UserTokenByJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginResponse loginResponse;

    @Autowired
    private User user;

    @Override
    public User register(User user) {
        // 检查用户的注册信息格式
        checkUserInfoIsLegal(user);

        // 检查用户是否已存在
        checkUserIsExist(user);

        user.setPassword(MD5EncodeUtil.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(String email, String password) {
        user = userRepository.findOne(email);

        if (user == null) {
            throw new IllegalParameterException("【邮箱】账户不存在");
        }
        if (!user.getPassword().equals(MD5EncodeUtil.encode(password))) {
            throw new IllegalParameterException("【密码】错误");
        }

        loginResponse.setUserToken(UserTokenByJwt.createToken(user));
        loginResponse.setUser(user);
        return loginResponse;
    }

    @Override
    public User changePassword(String userToken, String oldPassword, String newPassword) {
        user = UserTokenByJwt.parserToken(userToken, userRepository);
        if (!user.getPassword().equals(MD5EncodeUtil.encode(oldPassword))) {
            throw new IllegalParameterException("【原密码】错误");
        }

        user.setPassword(MD5EncodeUtil.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * 检查用户的注册信息格式
     *
     * @param user
     */
    private void checkUserInfoIsLegal(User user) {
        if (!checkEmailFormatIsPass(user.getEmail())) {
            throw new IllegalParameterException("【邮箱】格式非法，邮箱格式形如：example@domain");
        }
        if (!checkUsernameFormatIsPass(user.getUsername())) {
            throw new IllegalParameterException("【用户名】格式非法，请输入 5～20 位常规 ASCII 字符");
        }
        if (!checkPasswordFormatIsPass(user.getPassword())) {
            throw new IllegalParameterException("【密码】格式非法，请输入 5～20 位常规 ASCII 字符");
        }
    }

    /**
     * 检查用户邮箱格式
     */
    private Boolean checkEmailFormatIsPass(String email) {
        if (email == null || email.trim().equals("")) {
            return false;
        }
        if (!Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$").matcher(email).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户名格式
     */
    private Boolean checkUsernameFormatIsPass(String username) {
        if (username == null || username.trim().equals("")) {
            return false;
        }
        if (!Pattern.compile("^\\w{5,20}$").matcher(username).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检查密码格式
     */
    private Boolean checkPasswordFormatIsPass(String password) {
        if (password == null || password.trim().equals("")) {
            return false;
        }
        if (!Pattern.compile("^\\w{5,20}$").matcher(password).matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户是否已存在
     *
     * @param user
     * @return
     */
    private void checkUserIsExist(User user) {
        if (userRepository.findOne(user.getEmail()) != null) {
            throw new IllegalParameterException("【邮箱】已存在");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalParameterException("【用户名】已存在");
        }
    }
}
