package com.lee.jxmall.order.service.impl;

import com.lee.jxmall.order.entity.OrderEntity;
import com.lee.jxmall.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lee.common.utils.PageUtils;
import com.lee.common.utils.Query;

import com.lee.jxmall.order.dao.OrderItemDao;
import com.lee.jxmall.order.entity.OrderItemEntity;
import com.lee.jxmall.order.service.OrderItemService;


@RabbitListener(queues = {"java-queue"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 声明需要监听的所有队列
     *  参数类型：
     *      1、Message message：原生消息详细信息，头+体
     *      2、T<发送i消息的类型> OrderReturnReasonEntity
     *      3、Channel channel：当前传输数据的通道
     *
     *  Queue：可以有很多人监听，只要收到消息，队列就会删除消息，而且只能有一个收到此消息
     *      测试场景
     *          1）、订单服务启动多个，同一消息，只能有一个客户端收到
     *          2）、只有一个消息完全处理完，才会接下一个消息
     */
    //@RabbitListener(queues = {"java-queue"})
    @RabbitHandler
    public void recieveMessage(Message message, OrderReturnReasonEntity entity, Channel channel){

        byte[] body = message.getBody();
        //消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("内容："+entity);
        //模拟耗时长
       /* try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("消息处理完成，"+entity);

        //channel内按顺序自增
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("deliveryTag = "+deliveryTag);

        //签收货物，非批量模式
        try {
            if (deliveryTag%2==0) {
                //签收
                channel.basicAck(deliveryTag, false);
                System.out.println("签收了 " + deliveryTag);
            }else {
                //退货
                //参数：1、deliveryTag，2、批量操作，3、是否丢弃，false丢弃，true发回服务器，重新入队
                channel.basicNack(deliveryTag,false,true);
                //channel.basicReject();
                System.out.println("没签收 "+deliveryTag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    public void recieveMessage2(Message message,OrderEntity entity,Channel channel) throws InterruptedException{

        byte[] body = message.getBody();
        //消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("内容："+entity);
        //模拟耗时长
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("消息处理完成，"+entity);
    }

}
