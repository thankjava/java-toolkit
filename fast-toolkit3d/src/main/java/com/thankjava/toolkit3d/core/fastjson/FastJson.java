package com.thankjava.toolkit3d.core.fastjson;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thankjava.toolkit.core.reflect.BeanCopierUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Alibaba FastJson
 * 依赖于 maven[com.alibaba:fastjson]
 * <p>Function: FastJson</p>
 * <p>Description: </p>
 *
 * @author acexy@thankjava.com
 * @version 1.0
 * @date 2015年12月30日 下午12:01:26
 */
public class FastJson {

    /**
     * 将json字符串转换成对象
     * <p>Function: toObject</p>
     * <p>Description: </p>
     *
     * @param json
     * @param clazz
     * @return
     * @author acexy@thankjava.com
     * @date 2015年12月30日 下午12:01:44
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        T t = null;
        try {
            t = JSON.parseObject(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }

    /**
     * 将JSONObject 转成对象
     *
     * @param jsonObject
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(JSONObject jsonObject, Class<T> clazz) {
        if (jsonObject == null) {
            return null;
        }
        return JSONObject.toJavaObject(jsonObject, clazz);
    }

    /**
     * 将会解析json字符串，并将json中解析的数据
     * append到对象t中，原对象t中的属性不会丢失
     * <p>Function: appendObject</p>
     * <p>Description: </p>
     *
     * @param json
     * @param t
     * @return
     * @author acexy@thankjava.com
     * @date 2017年3月10日 下午3:01:58
     */
    public static <T> T appendObject(String json, T t) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) t.getClass();
        T tNew = toObject(json, clazz);
        BeanCopierUtil.copy(tNew, t);
        return tNew;
    }

    /**
     * JavaBean 2 JSONObject
     *
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object) {
        return (JSONObject) JSONObject.toJSON(object);
    }

    /**
     * JSON String 2 JSONObject
     *
     * @param json
     * @return
     */
    public static JSONObject toJSONObject(String json) {
        return JSONObject.parseObject(json);
    }

    /**
     * JavaBean 2 JSONArray
     *
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object) {
        return (JSONArray) JSONArray.toJSON(object);
    }

    /**
     * json 2 JSONArray
     *
     * @param json
     * @return
     */
    public static JSONArray toJSONArray(String json) {
        return JSONArray.parseArray(json);
    }

    /**
     * 对象转Map
     * <p>Function: toMap</p>
     * <p>Description: </p>
     *
     * @param object
     * @return
     * @author acexy@thankjava.com
     * @date 2016年3月8日 下午5:01:19
     */
    public static Map<String, Object> toMap(Object object) {
        if (object == null) {
            return null;
        }
        return toMap(toJSONString(object));
    }


    /**
     * 将jsonstr 转换成Map对象
     * <p>Function: toMap</p>
     * <p>Description: </p>
     *
     * @param json
     * @return
     * @author acexy@thankjava.com
     * @date 2016年1月19日 下午5:56:52
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(String json) {
        return (Map<String, Object>) JSON.parse(json);
    }

    /**
     * 将对象转json字符串
     * <p>Function: toJSONString</p>
     * <p>Description: </p>
     *
     * @param object
     * @return
     * @author acexy@thankjava.com
     * @date 2015年12月30日 下午12:26:29
     */
    public static String toJSONString(Object object) {
        if (object == null) {
            return null;
        }
        return JSONObject.toJSONString(object);
    }


    /**
     * 将对象转json字符串并格式美化输出
     * @param object json字符串或Javabean对象
     * @return
     */
    public static String toFormatJSONString(Object object) {

        if (object == null) {
            return null;
        }

        Object obj;

        if (object instanceof String) {
            obj = JSONObject.parse((String) object);
        } else {
            obj = JSONObject.toJSON(object);
        }

        return JSON.toJSONString(obj,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteDateUseDateFormat);
    }

}
