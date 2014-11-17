package com.tcl.aota.common.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * java对象序列化和反序列化 Created by kelong on 7/22/14.
 */
public class SerializeUtil {
	public final static Logger LOG = Logger.getLogger(SerializeUtil.class);

	/**
	 * java对象序列化位byte数组
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] bytes = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(object);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return bytes;
	}

	/**
	 * byte数组反序列化为对象
	 * 
	 * @param bytes
	 *            ：byte数组
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		Object obj = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);) {
			obj = ois.readObject();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return obj;
	}

}
