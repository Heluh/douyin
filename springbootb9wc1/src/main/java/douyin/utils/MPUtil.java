package douyin.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;

import cn.hutool.core.bean.BeanUtil;

import com.baomidou.mybatisplus.core.conditions.Wrapper; // 修改这里的导入

/**
 * Mybatis-Plus工具类
 */
public class MPUtil {
	public static final char UNDERLINE = '_';

	// mybatis plus allEQ 表达式转换
	public static Map<String, Object> allEQMapPre(Object bean, String pre) { // 修改方法签名
		Map<String, Object> map = BeanUtil.beanToMap(bean);
		return camelToUnderlineMap(map, pre);
	}

	// mybatis plus allEQ 表达式转换
	public static Map<String, Object> allEQMap(Object bean) { // 修改方法签名
		Map<String, Object> map = BeanUtil.beanToMap(bean);
		return camelToUnderlineMap(map, "");
	}

	public static Wrapper genLike(Wrapper wrapper, Map<String, String> param) {
		QueryWrapper queryWrapper = (QueryWrapper) wrapper; // 创建一个新的 QueryWrapper
		param.forEach((key, value) -> {
			queryWrapper.like(key, value);
		});
		return queryWrapper;
	}

	public static Wrapper likeOrEq(Wrapper wrapper, Object bean) { // 修改方法签名
		Map<String, Object> result = BeanUtil.beanToMap(bean, true, true);

		return genLikeOrEq(wrapper, result);
	}

	public static Wrapper genLikeOrEq(Wrapper wrapper, Map<String, Object> param) {
		QueryWrapper<Object> queryWrapper;
		if (wrapper instanceof QueryWrapper) {
			queryWrapper = (QueryWrapper<Object>) wrapper;
		} else {
			queryWrapper = new QueryWrapper<>();
		}

		for (Map.Entry<String, Object> entry : param.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value.toString().contains("%")) {
				queryWrapper.and(query -> query.like(StringUtils.isNotBlank((CharSequence) value), key, value));
			} else {
				queryWrapper.and(query -> query.eq(key, value));
			}
		}
		return queryWrapper;
	}

	public static Wrapper allEq(Wrapper wrapper, Object bean) { // 修改方法签名
		Map<String, Object> result = BeanUtil.beanToMap(bean, true, true);

		return genEq(wrapper, result);
	}

	public static Wrapper genEq(Wrapper wrapper, Map<String, Object> param) { // 修改方法签名
		QueryWrapper queryWrapper = (QueryWrapper) wrapper; // 创建一个新的 QueryWrapper
		param.forEach((key, value) -> {
			queryWrapper.eq(key, value);
		});
		return queryWrapper;
	}

	public static Wrapper between(Wrapper wrapper, Map<String, Object> params) {
		QueryWrapper queryWrapper = (QueryWrapper) wrapper; // 强制类型转换
		for (String key : params.keySet()) {
			String columnName = "";
			if (key.endsWith("_start")) {
				columnName = key.substring(0, key.indexOf("_start"));
				if (StringUtils.isNotBlank(params.get(key).toString())) {
					queryWrapper.ge(columnName, params.get(key));
				}
			}
			if (key.endsWith("_end")) {
				columnName = key.substring(0, key.indexOf("_end"));
				if (StringUtils.isNotBlank(params.get(key).toString())) {
					queryWrapper.le(columnName, params.get(key));
				}
			}
		}
		return queryWrapper;
	}

	public static Wrapper sort(Wrapper wrapper, Map<String, Object> params) {
		QueryWrapper queryWrapper = (QueryWrapper) wrapper; // 创建一个新的 QueryWrapper
		String order = "";
		if (params.get("order") != null && StringUtils.isNotBlank(params.get("order").toString())) {
			order = params.get("order").toString();
		}
		if (params.get("sort") != null && StringUtils.isNotBlank(params.get("sort").toString())) {
			if (order.equalsIgnoreCase("desc")) {
				queryWrapper.orderByDesc(params.get("sort").toString());
			} else {
				queryWrapper.orderByAsc(params.get("sort").toString());
			}
		}
		return queryWrapper;
	}

	/**
	 * 驼峰格式字符串转换为下划线格式字符串
	 *
	 * @param param
	 * @return
	 */
	public static String camelToUnderline(String param) {
		if (param ==        null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static void main(String[] ages) {
		System.out.println(camelToUnderline("ABCddfANM"));
	}

	public static Map<String, Object> camelToUnderlineMap(Map<String, Object> param, String pre) { // 修改方法签名
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = camelToUnderline(key);
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		return newMap;
	}
}
