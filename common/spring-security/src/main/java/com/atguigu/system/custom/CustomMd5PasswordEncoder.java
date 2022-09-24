package com.atguigu.system.custom;

import com.atguigu.common.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zzj
 * @date 2022/9/23
 */
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return MD5.encrypt(rawPassword.toString()).equals(encodedPassword);
    }
}
