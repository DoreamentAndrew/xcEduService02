package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description 发布订阅模式
 * @date 2022/11/12 15:07:40
 */
public class Producer02_publish {

//    队列名称
    public static final String QUEUE_INFORM_EMAIL = "queue_inform_email";
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";

    public static void main(String[] args) {

//        通过连接工厂创建一个新的连接,和生产者mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);//端口
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
//        设置虚拟机 一个mq可以设置多个虚拟机,每个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;
        try {
//        建立一个新的连接
            connection  = connectionFactory.newConnection();
//          创建一个会话通道,生产者和mq服务所有通信都在channel中完成
            channel = connection.createChannel();
//            申明一个队列,如果队列在mq中没有,则要创建一个
            /*
             * 参数明细
             * String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
             * 1.队列名称
             * 2.是否持久化, 如果持久化,mq重启后队列还在
             * 3.exclusive 是否独占连接,队列只允许在该连接中访问,如果连接关闭后,队列自动删除,如果此参数设置true,可以用于临时队列的创建
             * 4.autoDelete: 自动删除,队列不在使用时是否自动删除此队列,如果将此参数和exclution都设置为true的话,就可以实现临时队列(队列不用了就会自删除)
             * 5.arguments 参数,可以设置一个队列的扩展参数,比如,可以设置存活时间
             *
             * */
//            ctrl+art+鼠标左键可以弹出choose implementation提示框
            channel.queueDeclare(QUEUE_INFORM_EMAIL,true,false,false,null);
            channel.queueDeclare(QUEUE_INFORM_SMS,true,false,false,null);
//           申明一个交换机
            /**
             * 参数申明:(String exchange, String type)
             * 1.交换机名称
             * 2.交换机的类型
             * fanout: 对应的rabbit工作模式publish/Subscribe
             * direct: 对应的routing 工作模式
             * topic: 对应的就是通配符模式
             * headers:对应的header工作模式
             */
             channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT );
//             进行交换机和队列进行绑定
            /**
             * 参数申明:(String queue, String exchange, String routingKey)
             * 1.queue:队列名曾
             * 2.exchange:交换机名称
             * routingkey:  路由key,在发布订阅模式中设置为空串
             */

            channel.queueBind(QUEUE_INFORM_EMAIL,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_SMS ,EXCHANGE_FANOUT_INFORM,"");
//            发送消息
//            String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body
            /**
             * 参数明细:
             * 1.exchange:交换机,如果不指定的话,将使用默认mq的交换机,(设置为空"")
             * 2.routingKey:路由key,交换机根据路由key来将消息转发到指定的队列,如果使用默认的交换机,routingkey要设置为队列的名称
             * 3.props:消息的属性,
             * 4.body:消息的内容
             */
            for (int i = 0; i < 5; i++) {

//            消息内容
            String message = "send inform message to uesr";
            channel.basicPublish(EXCHANGE_FANOUT_INFORM,"",null,message.getBytes());
            System.out.println("sent to mq"+message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//        这是一首简单的小情歌这是一首简单的小请歌
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
