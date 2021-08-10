package com.lee.jxmall.order.config;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;


@Configuration
public class MyRabbitConfig {

    //@Qualifier
    private RabbitTemplate rabbitTemplate;

    /**
     * 当一个接口有2个不同实现时,使用@Autowired注解时会报
     *      org.springframework.beans.factory.NoUniqueBeanDefinitionException异常信息
     * 解决方法：
     *  （1）使用Qualifier注解，选择一个对象的名称,通常比较常用
     *  （2）Primary可以理解为默认优先选择,不可以同时设置多个,内部实质是设置BeanDefinition的primary属性
     *
     * @param connectionFactory
     * @return
     */
    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        initRabbitTemplate();
        return rabbitTemplate;
    }

    /**
     * 使用JSON序列化，进行消息转换
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){

        return new Jackson2JsonMessageConverter();
    }

    /**
     * 定制RabbitTemplate
     * 1、服务收到消息回调
     *      1、publisher-confirm-type: correlated
     *      2、设置确认回调 ConfirmCallback
     * 2、消息正确抵达队列进行回调
     *      1、yaml配置
     *          publisher-returns: true
     *          template.mandatory: true
     *      2、设置确认回调 ReturnsCallback
     *
     * 3、消费端确认（保证每个消息被正确消费，才可以broker删除这个消息）
     *      1、默认是自动确认的，只要消息接收到，客户端会自动确认，服务端就会移除这个消息
     *          问题：收到很多消息，自动回复给服务器ack，只有一个消息处理成功，宕机了，发生消息丢失
     *          所以，需要手动确认 acknowledge-mode: manual
     *          手动确认模式：只要没有明确告诉mq货物被签收，没有ACK，消息就一直是unacked状态，
     *              即使宕机，消息也不会丢失，会重新变为Ready，有新的消费段连接进来就发给他
     *      2、如何签收
     *           channel.basicAck(deliveryTag, false);          签收
     *           channel.basicNack(deliveryTag,false,true);     拒签
     *
     * @PostConstruct MyRabbitConfig对象创建完成后执行这个方法
     */
    //@PostConstruct
    public void initRabbitTemplate(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *  只要消息抵达Broker ack就为true 不管是否有消费者都会回调
             * @param correlationData   当前消息的唯一关联数据（这个消息的唯一ID）
             * @param b    ack          消息是否成功收到
             * @param s    cause        失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                /**
                 * 服务器收到了
                 */
                System.out.println("correlationData ==> "+correlationData+"，ack ==> "+b+"，cause ==> "+s);
            }
        });

        /**
         * 只要消息没有投递给指定的队列，就触发这个失败回调
         */
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             *  过时方法
             *      message       投递失败的消息详细信息
             *      replyCode     回复的状态码
             *      replyText     回复的文本内容
             *      exchange      当时这个消息发给哪个交换机
             *      routingKey    当时这个消息用哪个路由键
             *
             *  现用的方法
             * @param returnedMessage
             */
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                /**
                 * 报错误了， 修改数据库当前消息状态-->错误
                 */
                System.out.println("失败的消息："+returnedMessage);
            }
        });
    }

}
