package com.example.poplarannotate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 *
 * @ClassName: MapBeanUtil
 * @Description: map转bean，bean转map
 * @author FengTao
 * @date 2020年7月7日
 *
 */
public class MapBeanUtil {

  /**
   * 实体对象转成Map
   *
   * @param obj 实体对象
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static Map<String, Object> object2Map(Object obj) {
    Map<String, Object> map = new HashMap<>();
    if (obj == null) {
      return map;
    }
    Class clazz = obj.getClass();
    Field[] fields = clazz.getDeclaredFields();
    try {
      for (Field field : fields) {
        field.setAccessible(true);
        map.put(field.getName(), field.get(obj));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }

  /**
   * Map转成实体对象
   *
   * @param map   实体对象包含属性
   * @param clazz 实体对象类型
   * @return
   */
  public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
    if (map == null) {
      return null;
    }
    Object obj = null;
    try {
      obj = clazz.newInstance();

      Field[] fields = obj.getClass().getDeclaredFields();
      for (Field field : fields) {
        int mod = field.getModifiers();
        if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
          continue;
        }
        field.setAccessible(true);
        field.set(obj, map.get(field.getName()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return obj;
  }


  /**
   * 将json对象转换为HashMap
   * @param json
   * @return
   */
  public static Map<String, Object> parseJSON2Map(JSONObject json) {
    Map<String, Object> map = new HashMap<String, Object>();
    // 最外层解析
    for (Object k : json.keySet()) {
      Object v = json.get(k);
      // 如果内层还是json数组的话，继续解析
      if (v instanceof JSONArray) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Iterator<JSONObject> it = ((JSONArray) v).iterator();
        while (it.hasNext()) {
          JSONObject json2 = it.next();
          list.add(parseJSON2Map(json2));
        }
        map.put(k.toString(), list);
      } else if (v instanceof JSONObject) {
        // 如果内层是json对象的话，继续解析
        map.put(k.toString(), parseJSON2Map((JSONObject) v));
      } else {
        // 如果内层是普通对象的话，直接放入map中
        map.put(k.toString(), v);
      }
    }
    return map;
  }


  /**
   * 将json字符串转换为Map
   * @param jsonStr
   * @return
   */
  public static Map<String, Object> parseJSONstr2Map(String jsonStr) {
    JSONObject json = JSONObject.fromObject(jsonStr);
    Map<String, Object> map = parseJSON2Map(json);
    return map;
  }
}