package com.example.hanghaeplus.component.point;

import com.example.hanghaeplus.dto.product.ProductRequestForOrder;
import com.example.hanghaeplus.orm.entity.FakeUser;
import com.example.hanghaeplus.repository.order.Order;
import com.example.hanghaeplus.repository.point.Point;
import com.example.hanghaeplus.repository.user.User;
import com.example.hanghaeplus.repository.point.PointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PointManagerTest {

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointManager pointManager;

    @DisplayName("상품을 주문 한 후, 포인트 사용 내역을 저장 한다.")
    @Test
    void testProcessMethod() {
        // given
        User user = FakeUser.create(1L, "건희", 1000L);
        ProductRequestForOrder request = ProductRequestForOrder.of(1L, 10L, 5000L);

        Order order = Order.create(user, List.of(request));

        // when
        pointManager.process(user, order);

        // then
        Point expectedPoint = Point.create(user, order.getTotalPrice());
        verify(pointRepository, times(1)).save(expectedPoint);
    }
}
