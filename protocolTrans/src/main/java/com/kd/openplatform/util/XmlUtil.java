package com.kd.openplatform.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.XML;


public class XmlUtil {
    private static final Log log = LogFactory.getLog(XmlUtil.class);

    //解析xml
    public void resolveXml(String xml) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        //如果想对XML文件进行具体的操作，则可以调用下面的方法
        //1.遍历该XML文件
        //传入根元素获取名称和文本内容并判断是否存在子元素并获取信息
        ergodicDom(document.getRootElement());
    }

    //生成新的xml
    public String createXml(String xml, List<String> delNames) throws DocumentException {
        Document document = DocumentHelper.parseText(xml);
        for(String name : delNames){
            delElement(document.getRootElement(),name);
        }

        String rtn = document.asXML();
        log.info(rtn);
        return rtn;
    }

    //遍历XML文件
    public void  ergodicDom(Element element) {
        //获取文件中父元素的名称和文本内容
        log.info(element.getName() + "-----" + element.getTextTrim());
        //创建迭代器对象判断该父元素是否还有子元素，有的话，就获取子元素的名称和文本内容
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            //递归调用自身方法判断该子元素是否还存在子元素，以此类推并获取信息
            ergodicDom(e);
        }
    }

    //删除XML指定节点
    public void delElement(Element element,String domName) {
        //获取文件中父元素的名称和文本内容
        log.info(element.getName() + "-----" + element.getTextTrim());
        //创建迭代器对象判断该父元素是否还有子元素，有的话，就获取子元素的名称和文本内容
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element e = (Element) iterator.next();
            if(e.getName().equals(domName)){
                Element parent = e.getParent();
                //删除节点
                if(parent != null){
                    parent.remove(e);
                }

            }
            delElement(e,domName);
        }

    }


    public static String xml2json(String xmlStrint){
        org.json.JSONObject xmlJSONObj = null;
        try{
            xmlJSONObj = XML.toJSONObject(xmlStrint);
        }catch(JSONException e){
            log.info(e.getMessage());
        }

        return xmlJSONObj.toString();
    }

    public String json2xml(String jsonString){
        String rtn = null;
        Map<String,Object> map = new HashMap<String, Object>();
            net.sf.json.JSONObject jsObject = net.sf.json.JSONObject.fromObject(jsonString);
            Object[] names = jsObject.names().toArray();
            //取出第一个属性值作为根节点
            String rootNode = names[0].toString();
            //截取json字符串（删掉根节点属性，避免重复根节点）
            String jsString=jsonString.substring(jsonString.indexOf(":")+1,jsonString.lastIndexOf("}"));
            Document document = DocumentHelper.createDocument();
            Element root =  document.addElement(rootNode); //默认根节点
            //调用转换方法
        try {
            rtn = jsonToXml(jsString, root);
        }catch(Exception e){
            log.info(e.getMessage());
        }
            log.info(rtn);
        return rtn;
    }

    /**
     * 将json字符串转换成xml
     *
     * @param json     				json字符串
     * @param parentElement         xml根节点
     * @throws Exception
     */
    public String jsonToXml(String json, Element parentElement) throws Exception {
        JsonObject jsonObject = new com.google.gson.JsonParser().parse(json).getAsJsonObject();
        Element ee = toXml(jsonObject, parentElement, null);
        return ee.asXML();
    }

    /**
     * 将json字符串转换成xml
     *
     * @param jsonElement  		待解析json对象元素
     * @param parentElement		上一层xml的dom对象
     * @param name				父节点
     */
    public static Element toXml(JsonElement jsonElement, Element parentElement, String name) {
        if (jsonElement instanceof JsonArray) {
            //是json数据，需继续解析
            JsonArray sonJsonArray = (JsonArray)jsonElement;
            for (int i = 0; i < sonJsonArray.size(); i++) {
                JsonElement arrayElement = sonJsonArray.get(i);
                toXml(arrayElement, parentElement, name);
            }
        }else if (jsonElement instanceof JsonObject) {
            //说明是一个json对象字符串，需要继续解析
            JsonObject sonJsonObject = (JsonObject) jsonElement;
            Element currentElement = null;
            if (name != null) {
                currentElement = parentElement.addElement(name);
            }
            Set<Entry<String, JsonElement>> set = sonJsonObject.entrySet();
            for (Entry<String, JsonElement> s : set) {
                toXml(s.getValue(), currentElement != null ? currentElement : parentElement, s.getKey());
            }
        } else {
            //说明是一个键值对的key,可以作为节点插入了
            addAttribute(parentElement, name, jsonElement.getAsString());
        }
        return parentElement;
    }

    /**
     *
     * @param element  	父节点
     * @param name		子节点的名字
     * @param value		子节点的值
     */
    public static void addAttribute(Element element, String name, String value) {
        //增加子节点，并为子节点赋值
        Element el = element.addElement(name);
        el.addText(value);
    }

    public static void main(String args[]){
        XmlUtil xmlUtil = new XmlUtil();
        String xml = "<ORDER>\n" +
                "\t\t\t<SERVICECODE>0207115</SERVICECODE>\n" +
                "\t\t\t<source>03</source>\n" +
                "\t\t\t<target>37101</target>\n" +
                "\t\t\t<data>\n" +
                "\t\t\t\t<preAppNo>031012019032999999</preAppNo>\n" +
                "\t\t\t\t<channelType>03</channelType>\n" +
                "\t\t\t\t<proCode>370000</proCode>\n" +
                "\t\t\t\t<cityCode>370100</cityCode>\n" +
                "\t\t\t\t<orgNo>374010401</orgNo>\n" +
                "\t\t\t\t<countyCode>370101</countyCode>\n" +
                "\t\t\t\t<elecAddr>北京人济大厦</elecAddr>\n" +
                "\t\t\t\t<busiTypeCode>101</busiTypeCode>\n" +
                "\t\t\t\t<custType>03</custType>\n" +
                "\t\t\t\t<consName>李曜</consName>\n" +
                "\t\t\t\t<schargerSchemeLists>\n" +
                "\t\t\t\t\t<schargerSchemeList>\n" +
                "\t\t\t\t\t\t<typeCode>016</typeCode>\n" +
                "\t\t\t\t\t\t<madeNo>000000</madeNo>\n" +
                "\t\t\t\t\t\t<factory>默认厂家</factory>\n" +
                "\t\t\t\t\t\t<madeDate>2019-01-01</madeDate>\n" +
                "\t\t\t\t\t\t<rcCode>258V</rcCode>\n" +
                "\t\t\t\t\t\t<rvCode>200A</rvCode>\n" +
                "\t\t\t\t\t\t<maxOutVolt>250V</maxOutVolt>\n" +
                "\t\t\t\t\t\t<rMaxCcCode>32A</rMaxCcCode>\n" +
                "\t\t\t\t\t\t<maxPower>3699W</maxPower>\n" +
                "\t\t\t\t\t\t<modelCode>充电桩</modelCode>\n" +
                "\t\t\t\t\t\t<chgCesc>01</chgCesc>\n" +
                "\t\t\t\t\t</schargerSchemeList>\n" +
                "\t\t\t\t</schargerSchemeLists>\n" +
                "\t\t\t</data>\n" +
                "\t\t</ORDER>";

        String json = "{\"ORDER\":{\"data\":{\"countyCode\":370101,\"preAppNo\":31012019032999999,\"proCode\":370000,\"orgNo\":374010401,\"elecAddr\":\"北京人济大厦\",\"cityCode\":370100,\"custType\":3,\"busiTypeCode\":101,\"channelType\":3,\"consName\":\"李曜\",\"schargerSchemeLists\":{\"schargerSchemeList\":{\"madeNo\":0,\"rMaxCcCode\":\"32A\",\"factory\":\"默认厂家\",\"madeDate\":\"2019-01-01\",\"rcCode\":\"258V\",\"maxOutVolt\":\"250V\",\"rvCode\":\"200A\",\"modelCode\":\"充电桩\",\"chgCesc\":1,\"maxPower\":\"3699W\",\"typeCode\":14}}},\"SERVICECODE\":69197,\"source\":3,\"target\":37101}}";
        log.info(xmlUtil.json2xml(json));
        log.info(xmlUtil.xml2json(xml));


    }
}
