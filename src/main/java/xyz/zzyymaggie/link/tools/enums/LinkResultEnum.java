/**
 * Copyright © zzyymaggie. All Rights Reserved.
 */
package zzyymaggie.xyz.link.tools.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 类 LinkResultEnum.java 的实现描述 : url 访问结果枚举类型
 * 
 * @author zhangyu
 * 
 * @date 2014年5月22日 上午10:49:50
 */
public enum LinkResultEnum {
    /**
     * 非标准URL
     */
    NOT_STANDARD_URL("NOT_STANDARD_URL"),
	/**
	 * 访问成功
	 */
	SUCCESS("SUCCESS"),

	/**
	 * 访问失败
	 */
	NO_ACCESS("NO_ACCESS"),

	/**
	 * 连接超时
	 */
	CONNECT_TIMEOUT("CONNECT_TIMEOUT");

	private String type;

	private static Map<String, LinkResultEnum> map = new HashMap<String, LinkResultEnum>();

	static {
		for (LinkResultEnum type : LinkResultEnum.values()) {
			map.put(type.getType(), type);
		}
	}

	private LinkResultEnum(String type) {
		this.type = type;
	}

	public static LinkResultEnum parse(String type) {
		if (type == null) {
			return null;
		}
		return map.get(type);
	}

	public String getType() {
		return type;
	}

	public String toString() {
		return type;
	}
}
