package org.athena.common.util;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * RabbitmqConfiguration 工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RabbitmqUtil {

    private static Logger logger = LoggerFactory.getLogger(RabbitmqUtil.class);

    private static volatile Connection connection;

    /**
     * 创建 RabbitmqConfiguration 链接
     *
     * @param host        域名
     * @param username    用户名
     * @param password    密码
     * @param virtualHost RabbitmqConfiguration 虚拟主机
     */
    public static Connection getConnection(String host, String username, String password, String virtualHost)
            throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connection = connectionFactory.newConnection();
        return connection;
    }

    /**
     * 消费
     */
    private static void test() throws IOException {
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare("test_simple_queue", false, false, false, null);
        // 创建消费者
        Consumer consumer = new DefaultConsumer(channel) {
            // 获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                logger.info("接收到消息——" + msg);

                // 返回确认状态
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听队列
        channel.basicConsume("test_simple_queue", false, consumer);
    }

    /**
     * 生产
     */
    private static void send() throws IOException, InterruptedException {
        // 获取连接
        Connection connection = null;
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明一个队列
        channel.queueDeclare("test_simple_queue", false, false, false, null);
        // 消息内容
        String msg = "simple queue hello!";

        ExecutorService service = Executors.newFixedThreadPool(24);
        for (int i = 0; i < 1; i++) {
            service.submit(() -> {
                try {
                    channel.basicPublish("", "test_simple_queue", null, msg.getBytes());
                    logger.info("线程: {} 正发送消息", Thread.currentThread().getName());
                } catch (IOException e) {
                    logger.error("", e);
                }
            });
        }
        // 发送消息
        service.shutdown();
        if (service.awaitTermination(2, TimeUnit.DAYS)) {
            logger.info("send success");
            connection.close();
        }
    }


}
