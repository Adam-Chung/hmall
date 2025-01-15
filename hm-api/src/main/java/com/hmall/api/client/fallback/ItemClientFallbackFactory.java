package com.hmall.api.client.fallback;

import java.util.Collection;
import java.util.List;

import org.springframework.cloud.openfeign.FallbackFactory;

import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import com.hmall.common.utils.CollUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable cause) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("查詢商品失敗", cause);
                return CollUtils.emptyList(); //查詢商品失敗，返回空集合
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("扣庫存失敗", cause);
                throw new RuntimeException("扣庫存失敗", cause);
            }
        };
    }
}
