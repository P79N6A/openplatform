package com.kd.openplatform.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * @author zhangsg
 * 参数类型转换
 */
public class ParamsAdapter extends JSONObject{
	private static final Log log =  LogFactory.getLog(ParamsAdapter.class);
	/**
	 * 将数据转换为相应的参数类型
	 * @param type
	 * @param o
	 * @return
	 * @throws ParseException 
	 */
	public static Object typeCastParams(String type,Object o) throws ParseException {
		Object obj = o; 
		switch (type) {
		case"String":
		case"string":
		case "java.lang.String":
			obj = o==null?"":o.toString();
			break;
		case "byte":
		case "Byte":
		case "java.lang.Byte":
			obj = Byte.parseByte(o.toString());
			break;
		case "short":
		case "Short":
		case "java.lang.Short":
			obj = Short.parseShort(o.toString());
			break;
		case "int":	
		case "Integer":
		case "java.lang.Integer":
			obj = Integer.parseInt(o.toString());
			break;
		case "float":
		case "Float":
		case "java.lang.Float":
			obj = Float.parseFloat(o.toString());
			break;
		case "long":
		case "Long":
		case "java.lang.Long":
			obj = Long.parseLong(o.toString());
			break;
		case "double":
		case "Double":
		case "java.lang.Double":
			obj = Double.parseDouble(o.toString());
			break;	
		case "BigDecimal":
		case "bigdecimal":
		case "java.math.BigDecimal":
			obj = new BigDecimal(o.toString());
			break;	
		case "boolean":
		case "Boolean":
		case "java.lang.Boolean":
			obj = Boolean.parseBoolean(o.toString());
			break;	
		case "Date":
		case "date":
		case "java.util.Date":
//			obj = new Date(o.toString());
			obj = DateFormat.getDateTimeInstance().parse(o.toString());
			break;
		case "list[string]":
			obj =  parseArray(o.toString(), String.class);
			break;
		case "list[int]":
			obj =  parseArray(o.toString(), Integer.class);
			break;
		default:
			log.info("其他类型  :"+type);
			break;
		}
		return obj;
	}
	
	
	//稍后改成读数据库字典表
	public static String getType(String data_type) {
		String type = "string";
		switch (data_type) {
		case "0":
			type = "string";
			break;
		case "1":
			type = "int";
			break;
		case "2":
			type = "boolean";
			break;
		case "3":
			type = "object";
			break;
		case "4":
			type = "float";
			break;
		case "5":
			type = "double";
			break;
		case "6":
			type = "byte";
			break;
		case "7":
			type = "short";
			break;
		case "8":
			type = "date";
			break;
		case "9":
			type = "long";
			break;
		case "10":
			type = "list[string]";
			break;
		case "11":
			type = "list[int]";
			break;
		case "12":
			type = "bigdecimal";
			break;
		default:
			log.info("其他类型  :"+data_type);
			break;
		}
		return type;
	}
}
