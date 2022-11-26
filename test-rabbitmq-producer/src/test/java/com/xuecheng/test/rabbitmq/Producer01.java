package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description rabbitmq的入门程序
 * @date 2022/10/27 14:47:45
 */
public class Producer01 {
//    队列
    private static  final String QUEUE = "helloworld";
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
            channel.queueDeclare(QUEUE,true,false,false,null);
//            发送消息
//            String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body
            /**
             * 参数明细:
             * 1.exchange:交换机,如果不指定的话,将使用默认mq的交换机,(设置为空"")
             * 2.routingKey:路由key,交换机根据路由key来将消息转发到指定的队列,如果使用默认的交换机,routingkey要设置为队列的名称
             * 3.props:消息的属性,
             * 4.body:消息的内容
             */
//            消息内容
            String message = "hello world 黑马程序员";
            channel.basicPublish("",QUEUE,null,message.getBytes());
            System.out.println("sent to mq"+message);

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
