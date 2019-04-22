package cn.itcast.web.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;


public class EmailProducer {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination emailQueue;
    public void send(String to,String subject,String content){
        jmsTemplate.send(emailQueue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setStringProperty("to",to);
                mapMessage.setStringProperty("subject",subject);
                mapMessage.setStringProperty("content",content);
                return mapMessage;
            }
        });
    }

}
