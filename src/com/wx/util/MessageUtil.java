package com.wx.util;

import com.thoughtworks.xstream.XStream;
import com.wx.po.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static Map<String, String> xml2Map(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream ins = null;
        try {
            ins = request.getInputStream();
            Document doc = reader.read(ins);
            Element root = doc.getRootElement();
            List<Element> elements = root.elements();
            for (Element e :
                    elements) {
                map.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != ins) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }

    public static String textMessage2Xml(TextMessage textMessage){
        XStream xStream = new XStream();
        // xml根节点要是"xml"
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

}
