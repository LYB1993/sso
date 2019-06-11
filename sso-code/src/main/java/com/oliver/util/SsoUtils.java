package com.oliver.util;

import com.oliver.bean.SsoObject;
import com.oliver.bean.User;
import com.oliver.bean.eo.Constant;
import com.oliver.bean.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

/**
 * com.oliver.util SsoUtils
 *
 * @author Lyb
 * @version 1.0
 * @date 2019/5/30 15:00
 */
public final class SsoUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(SsoUtils.class);

    private final static String SEPARATOR_UNDERLINE = "_";

    private SsoUtils() {

    }

    /**
     * 通过用户对象生成唯一的sessionid
     *
     * @param ssoObject ssoObject
     * @return sessionId
     */
    public static String generateSessionId(SsoObject ssoObject) {
        return ssoObject.getUserName().concat(SEPARATOR_UNDERLINE).concat(ssoObject.getVersion());
    }

    /**
     * 封装单点用户对象
     *
     * @param userResult 用户验证返回的数据
     * @return SsoUser对象
     */
    public static SsoObject buildSsoUser(Result<User> userResult) {
        SsoObject ssoObject = new SsoObject();
        ssoObject.setUserName(userResult.getData().getUserName());
        ssoObject.setPassWord(userResult.getData().getPassWord());
        ssoObject.setVersion(UUID.randomUUID().toString());
        ssoObject.setLoginTime(System.currentTimeMillis());
        return ssoObject;
    }

    /**
     * 对象序列化方法
     *
     * @param object 需要序列化的对象
     * @return 序列化的对象数据
     */
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream)) {
            objectOutputStream.writeObject(object);
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Object serialize failure:{}", e.getMessage());
                e.printStackTrace();
            }
            LOGGER.error("Object serialize failure:{}", e.getMessage());
        } finally {
            closeStream(arrayOutputStream);
        }
        return new byte[0];
    }

    /**
     * 反序列化对象
     *
     * @param bytes 对象的字符数据
     * @return 反序列化后的对象
     */
    public static Object deserialization(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
            try (ObjectInputStream objectInputStream = new ObjectInputStream(arrayInputStream)) {
                return objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("deserialization failure. Exception:{}", e.getMessage());
                    e.printStackTrace();
                }
                LOGGER.error("deserialization failure. Exception:{}", e.getMessage());
            } finally {
                closeStream(arrayInputStream);
            }
        }
        return null;
    }

    /**
     * 关闭流
     *
     * @param stream 需要关闭的流
     */
    private static void closeStream(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Object Stream close failure:{}", e.getMessage());
                e.printStackTrace();
            }
            LOGGER.debug("Object Stream close failure:{}", e.getMessage());
        }
    }

    /**
     * 重定向地址添加参数
     *
     * @param redirectUrl 地址
     * @param sessionId   添加的请求参数
     * @return 新地址
     */
    public static String enhanceRedirectUrl(String redirectUrl, String sessionId) {
        return redirectUrl
                .concat("?")
                .concat(Constant.COOKIE_NAME.getValue())
                .concat("=")
                .concat(sessionId);
    }

}
