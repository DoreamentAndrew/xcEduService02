package com.xuecheng.test.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Andrewer
 * @version 1.0
 * @project xcEduService01
 * @description
 * @date 2022/11/12 18:10:09
 */
public class Consumer03_routing_sms {
    //    队列名称
    public static final String QUEUE_INFORM_SMS = "queue_inform_sms";
    public static final String EXCHANGE_ROUTING_INFORM = "exchange_routing_inform";
    public static final String ROUTINGKEY_SMS = "routing_sms ";
    public static void main(String[] args) throws IOException, TimeoutException {

//        通过连接工厂创建新的连接功能和mq建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
//        设置虚拟机,一个mq可以设置多个虚拟机,每一个虚拟机相当于一个独立的mq
        connectionFactory.setVirtualHost("/");

//        建立连接
        Connection connection  =connectionFactory.newConnection();
//        创建会话通道,生产者和mq服务所有的通信都在chanel中完成
        Channel channel = connection.createChannel();
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
        channel.exchangeDeclare(EXCHANGE_ROUTING_INFORM, BuiltinExchangeType.DIRECT );
//             进行交换机和队列进行绑定
        /**
         * 参数申明:(String queue, String exchange, String routingKey)
         * 1.queue:队列名曾
         * 2.exchange:交换机名称
         * routingkey:  路由key,在发布订阅模式中设置为空串
         */

        channel.queueBind(QUEUE_INFORM_SMS,EXCHANGE_ROUTING_INFORM,ROUTINGKEY_SMS);

//        实现消费方法
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
//

            /**
             *   当接受到消息之后,此方法,将被调用
             * @param consumerTag 消费者标签:用来表示消费者  ,在建通队列的售后设置channel.basicConsumer
             * @param envelope    信封,通过envelope
             * @param properties  消息属性
             * @param body        消息内容
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                交换机
                String exchange = envelope.getExchange();
//                消息id,mq在通道中用来表示消息的id ,可用于确认消息已接受
                long deliveryTag = envelope.getDeliveryTag();
//                消息内容
                String message = new String(body,"utf-8");
                System.out.println("receive message :"+message);
            }
        };

//        监听队列
//        参数: String queue, boolean autoAck, Consumer callback
        /**
         * 参数明细
         * queue:队列名称
         * autoAck:自动回复,当消费者接收到消息之后,要告诉mq消息已接受,如果将此参数设置为true表示会自动回复mq,如果设置为false,要通过编程实现回鹘
         * callback:,消费方法,当消费者接受到消息时,需要执行的方法
         */
        channel.basicConsume(QUEUE_INFORM_SMS,true,defaultConsumer);
    }

}
