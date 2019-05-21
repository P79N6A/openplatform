package com.kd.openplatform.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
	
	protected final static Log logger = LogFactory.getLog(JsonUtil.class);
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static <T> String objectToJsonString(T t) {
		String json = "";
		try {
			json = mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("JsonUtil:",e);
		}

		return json;
	}
	//牛延军
	//这个方法写的不好 ，有空优化
	//考虑到{"a":"a","b":{"b1":{"b2":"b","e":"e"}},"c":"c","d":[{"d1":"d1","d2":"d2"},{"d1":"d2","d2":"d22"}]}这种结构中a，b2,d要做资源控制delNames的形式应该是["a","b;b1;b2";"d[];d1"]
	public String createJson(String str ,List<String> delNames){
		String rtn = null;
		JSONObject jsonObject = JSONObject.parseObject(str);
		JSONObject tmpObj = null;
		for(String tmp : delNames){
			//如果没有“;”说明是第一层，类似“a”这种，直接删除
			if(!tmp.contains(";")){
				jsonObject.remove(tmp);
			}else if(!tmp.contains("[]")){
				//如果是嵌套的不知道怎么删，就把内层的删掉后再加进来
				String names[] = tmp.split(";");
				JSONObject[] jsonArr = new JSONObject[names.length-1];
				for (int i = 0, l = names.length; i < l; i++) {
					if(i==0){
						jsonArr[i] = jsonObject.getJSONObject(names[i]);

						System.out.println(jsonArr[i]+"---"+names[i]);
					}else if(i <= l - 2) {
						jsonArr[i] = jsonArr[i-1].getJSONObject(names[i]);
//						System.out.println(jsonArr[i]+"---"+names[i]);
					}
				}
				jsonArr[jsonArr.length-1].remove(names[names.length - 1]);
				jsonObject.put(names[jsonArr.length - 1],jsonArr[jsonArr.length-1]);
			}
//			if(tmp.contains("[]")){
//
//			}
		}
		return jsonObject.toJSONString();
	}
}
