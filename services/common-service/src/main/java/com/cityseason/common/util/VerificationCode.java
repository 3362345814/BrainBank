package com.cityseason.common.util;

import com.cityseason.common.annotation.AddCache;
import com.cityseason.common.annotation.DelCache;

/**
 * 验证码工具类
 */
public class VerificationCode {

    /**
     * 生成验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    @AddCache(prefix = "user:verificationCode", expire = 300)
    public static String generate(String phone) {
        // 生成6位随机数字验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        // TODO: 发送验证码到手机


        return code;
    }

    /**
     * 验证验证码
     *
     * @param code  验证码
     * @param phone 手机号
     * @return 是否验证通过
     */
    @DelCache(prefix = "user:verificationCode")
    public static boolean verify(String phone, String code) {
        // TODO: 验证验证码是否正确

        return true;
    }

}
