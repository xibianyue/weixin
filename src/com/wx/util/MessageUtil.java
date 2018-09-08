package com.wx.util;

import com.thoughtworks.xstream.XStream;
import com.wx.po.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";

    /**
     * 将xml转换为map集合
     * @param request
     * @return
     */
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

    /**
     * 将textmessage转换为xml
     * @param textMessage
     * @return
     */
    public static String textMessage2Xml(TextMessage textMessage){
        XStream xStream = new XStream();
        // xml根节点要是"xml"
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    public static String initText(String toUserName, String fromUserName, String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setMsgType(MESSAGE_TEXT);
        textMessage.setCreateTime(new Date().getTime()+"");
        return textMessage2Xml(textMessage);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照提示进行操作：\n\n");
        sb.append("1、课程介绍\n");
        sb.append("2、慕课网介绍\n\n");
        sb.append("回复？调出此菜单。");
        return sb.toString();
    }

    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("第一个菜单");
        return sb.toString();
    }

    public static String secondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("第二个菜单");
        return sb.toString();
    }

}
