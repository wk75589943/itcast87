package cn.itcast.jms;

import cn.itcast.common.utils.MailUtil;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

public class EmailListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mm = (MapMessage) message;
            String to = mm.getStringProperty("to");
            String subject = mm.getStringProperty("subject");
            String content = mm.getStringProperty("content");
            System.out.println(to);
            System.out.println(subject);
            System.out.println(content);
            MailUtil.sendMsg(to,subject,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
