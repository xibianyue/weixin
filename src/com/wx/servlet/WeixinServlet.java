package com.wx.servlet;

import com.wx.po.TextMessage;
import com.wx.util.CheckUtil;
import com.wx.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

public class WeixinServlet extends HttpServlet {

    /**
     * 对接微信会首先执行此验证
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        PrintWriter out = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echostr);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        try {
            Map<String, String> map = MessageUtil.xml2Map(req);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            String message = null;
            if ("text".equals(msgType)) {
                TextMessage textMessage = new TextMessage();
                textMessage.setContent("您发送的消息是："+content);
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setMsgType("text");
                textMessage.setCreateTime(new Date().getTime()+"");
                message = MessageUtil.textMessage2Xml(textMessage);
            }
            writer.print(message);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
