package com.hmall.trade.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.hmall.api.client.PayClient;
import com.hmall.api.dto.PayOrderDTO;
import com.hmall.trade.constants.MQconstants;
import com.hmall.trade.domain.po.Order;
import com.hmall.trade.service.IOrderService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderDelayMessageListener {

    private final IOrderService orderService;
    private final PayClient payClient;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQconstants.DELAY_ORDER_QUEUE_NAME, durable = "true"),
            exchange = @Exchange(name = MQconstants.DELAY_EXCHANGE_NAME,delayed = "true",type = ExchangeTypes.DIRECT),
            key = MQconstants.DELAY_ORDER_KEY
    ))
    public void listenOrderDelayMessage(Long orderId){
        // 1. 查詢訂單
        Order order = orderService.getById(orderId);

        // 2. 檢測訂單狀態，判斷是否已支付
        if(order == null || order.getStatus() != 1){
            // 訂單不存在或者已經支付，不處理
            return;
        }

        // 3. 未支付，需要查詢支付流水狀態
        PayOrderDTO payOrder = payClient.queryPayOrderByBizOrderNo(orderId);

        // 4. 判斷是否支付
        if(payOrder != null && payOrder.getStatus() == 3){
            // 4.1 已支付，更新訂單狀態 為 已支付
            orderService.markOrderPaySuccess(orderId);
        }else{
            // 4.2 未支付，取消訂單 回復庫存
            orderService.cancelOrder(orderId);
        }


    }
}
