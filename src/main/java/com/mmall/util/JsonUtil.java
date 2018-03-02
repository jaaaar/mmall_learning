package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by YuWen
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入序列化
        objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);
        //取消默认转换timestamps形式  (json序列化时会默认把DATE转换为TIMESTAMPS类型,现设置取消这种行为)
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一,  即yyyy年MM月dd日 HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));


        //反序列化时 忽略在json文件中存在,但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    //序列化
    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object to String error ", e);
            return null;
        }
    }


    //经过格式化的序列化
    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.
                    writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse Object to String error ", e);
            return null;
        }
    }


    //反序列化
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error ", e);
            return null;
        }
    }

    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            //  return后整个代码段强转为T
            return (T) (typeReference.getType().equals(String.class) ? (T) str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error ", e);
            return null;
        }
    }

    public static <T> T stringToObj(String str, Class<?> collectionCLass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionCLass, elementClasses);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error ", e);
            return null;
        }
    }


    public static void main(String[] args) {
        User user = new User();
//        user.setId(1);
//        user.setEmail("yuwen@gmail.com");
//        user.setCreateTime(new Date());

        String userJsonPretty = JsonUtil.objToStringPretty(user);
        log.info("userJson: {}", userJsonPretty);

//        User user2 = new User();
//        user2.setId(2);
//        user2.setEmail("yuwenu2@gmail.com");
//
//        String userJson = JsonUtil.objToString(user);
//        String userJsonPretty = JsonUtil.objToStringPretty(user);
//
//        log.info("userJson: {}", userJson);
//        log.info("userJsonPretty :{}", userJsonPretty);
//
//
//        User user1 = JsonUtil.stringToObj(userJson, User.class);
//
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(user);
//        userList.add(user2);
//
//        String userListStr = JsonUtil.objToStringPretty(userList);
//
//        log.info("=====================================");
//        log.info(userListStr);
//
//        List<User> userListObj1 = JsonUtil.stringToObj(userListStr, new TypeReference<List<User>>(){});
//        List<User> userListObj1 = JsonUtil.stringToObj(userListStr, new TypeReference<List<User>>() {
//        });

//        List<User> userListObj2 = JsonUtil.stringToObj(userListStr, List.class, User.class);
        System.out.println("end");
    }


}
