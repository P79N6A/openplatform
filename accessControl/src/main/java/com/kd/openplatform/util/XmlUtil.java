package com.kd.openplatform.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

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
    public String createXml(String xml, JSONObject sourceControlObj) throws DocumentException {
        Set<String> keys = sourceControlObj.keySet();
        Document document = DocumentHelper.parseText(xml);
        for (String key : keys) {
            String value = sourceControlObj.getString(key);
            if (value.equals("-999") || value.equals("") || value.equals("NULL")) {
                delElement(document.getRootElement(), value);
            } else {

            }
        }
        String rtn = document.asXML();
        log.info(rtn);
        return rtn;
    }

    public void getRootAttr(String xml){
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            System.out.println(root.attributeValue("appId"));
            System.out.println(root.attributeValue("apiDomainName"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
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

    public static void main(String args[]){
        XmlUtil xmlUtil = new XmlUtil();
        String xml = " <chargeArchives appId=\"4028dc8169dcc2010169dcc76410000d\" apiDomainName = \"chargeArchivesService\">\n" +
                "\t<target>11102 </target>\n" +
                "\t<measBusiType>0</measBusiType>\n" +
                "\t<consNo>3024577</consNo>\n" +
                "\t<packType>02</packType>\n" +
                "\t<tgName>武汉市江岸区高教园</tgName>\n" +
                "\t<voltageLevel> AC02202</voltageLevel>\n" +
                "\t<stationId> d84f03e84e724958b404ddbbed51b1cc </stationId>\n" +
                "\t<stationName>武汉高教园充电站</stationName>\n" +
                "\t<stationAddr>武汉市江岸区高教园</stationAddr>\n" +
                "\t<stationType>01</stationType>\n" +
                "\t<stakeNums>5</stakeNums>\n" +
                "\t<lat>116.303706</lat>\n" +
                "\t<lng>34.27814</lng>\n" +
                "\t<principallist>\n" +
                "\t\t<principal>\n" +
                "\t\t<principalType>01</principalType>\n" +
                "\t\t<otherPrincipalTypeRemark></otherPrincipalTypeRemark>\n" +
                "\t\t<principalName>湖南省电力公司</principalName>\n" +
                "\t\t</principal>\n" +
                "\t\t<principal>\n" +
                "\t\t\t<principalType>01</principalType>\n" +
                "\t\t\t<otherPrincipalTypeRemark></otherPrincipalTypeRemark>\n" +
                "\t\t\t<principalName>湖北省电力公司</principalName>\n" +
                "\t\t</principal>\n" +
                "\t</principallist>\n" +
                "\t<stakelist>\n" +
                "\t\t<stake>\n" +
                "\t\t\t<stakeId>1504773001</stakeId>\n" +
                "\t\t\t<stakeName>高教园1号桩</stakeName>\n" +
                "\t\t\t<stakeType>交流</stakeType>\n" +
                "\t\t\t<ratedPower>500</ratedPower>\n" +
                "\t\t\t<stakeAssetNO>1504773001</stakeAssetNO>\n" +
                "\t\t\t<supplier>供应商</supplier>\n" +
                "\t\t</stake>\n" +
                "\t\t<stake>\n" +
                "\t\t\t<stakeId>1504773002</stakeId>\n" +
                "\t\t\t<stakeName>高教园2号桩</stakeName>\n" +
                "\t\t\t<stakeType>交流</stakeType>\n" +
                "\t\t\t<ratedPower>500</ratedPower>\n" +
                "\t\t\t<stakeAssetNO>1504773002</stakeAssetNO>\n" +
                "\t\t\t<supplier>供应商</supplier>\n" +
                "\t\t</stake>\n" +
                "\t</stakelist>\n" +
                "\t<mpList>\n" +
                "\t<elecPriceNo></elecPriceNo>\n" +
                "\t\t<mpid> </mpid>\n" +
                "\t\t<elecPriceNo> </elecPriceNo>\n" +
                "\t\t<mpName>站总表</mpName>\n" +
                "\t\t<mpType>考核计量点</mpType>\n" +
                "\t\t<stakeId>0</stakeId> \n" +
                "\t\t<meterlist>\n" +
                "\t\t\t<meter>\n" +
                "\t\t\t\t<meterAssetNO>58271091235</meterAssetNO>\n" +
                "\t\t\t\t<mpid> </mpid>\n" +
                "\t\t\t\t<factor>1</factor>\n" +
                "\t\t\t</meter>\n" +
                "\t\t</meterlist>\n" +
                "\t\t<itAssetlist>\n" +
                "\t\t\t<it>\n" +
                "\t\t\t\t<itAssetNO>58271091235</itAssetNO>\n" +
                "\t\t\t\t<mpid>1</mpid>\n" +
                "\t\t\t</it>\n" +
                "\t\t</itAssetlist>\t\n" +
                "\t</mpList>\n" +
                "\t<catPrclist>\n" +
                "\t\t<catPrc>\n" +
                "\t\t\t<prcTl>01</prcTl>\n" +
                "\t\t\t<unitprice>1.813</unitprice>\n" +
                "\t\t\t<elecPriceNo>电价码</elecPriceNo>\n" +
                "\t\t\t<elecPriceName>电价</elecPriceName>\n" +
                "\t\t</catPrc>\n" +
                "\t\t<catPrc>\n" +
                "\t\t\t<prcTl>02</prcTl>\n" +
                "\t\t\t<unitprice>1.113</unitprice>\n" +
                "\t\t\t<elecPriceNo>电价码</elecPriceNo>\n" +
                "\t\t</catPrc>\n" +
                "\t</catPrclist>\n" +
                "\t<elecUnitPriceList>\n" +
                "\t\t<capPrc>23</capPrc>           \n" +
                "\t\t<dmdPrc>12</dmdPrc>\n" +
                "\t\t<elecPriceNo>1213131</elecPriceNo>\n" +
                "\t</elecUnitPriceList>\n" +
                "</chargeArchives> ";
        try {
            //xmlUtil.resolveXml(xml);
            List<String> names = new ArrayList<String>();
            names.add("maxOutVolt");
            names.add("modelCode");
            names.add("consName");
            names.add("elecAddr");
            names.add("schargerSchemeLists");
            names.add("madeDate");
            xmlUtil.getRootAttr(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
