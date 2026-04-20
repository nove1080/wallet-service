package com.payment.wallet_service.wallet.infrastructure.kafka;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.payment.wallet_service.wallet.domain.SettlementCompletedEvent;
import com.payment.wallet_service.wallet.domain.WalletEventMessage;
import com.payment.wallet_service.wallet.domain.WalletEventMessage.MessageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootTest
@ActiveProfiles("test")
class KafkaWalletEventPublisherTest {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @MockitoBean
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    @DisplayName("트랜잭션 내에서 이벤트를 발행했더라도 롤백되면 카프카로 전송되지 않는다.")
    void whenTransactionRollsBack_thenKafkaEventShouldNotBePublished() {
        //given
        WalletEventMessage message = WalletEventMessage.builder()
            .orderId("test-order-123")
            .type(MessageType.SETTLEMENT_SUCCESS)
            .build();
        SettlementCompletedEvent event = SettlementCompletedEvent.builder().message(message).build();

        //when: 트랜잭션 내에서 예외 발생
        try {
            transactionTemplate.executeWithoutResult(status -> {
                eventPublisher.publishEvent(event);
                throw new RuntimeException("DB 처리 중 강제 예외 발생");
            });
        } catch (RuntimeException ignored) {

        }

        //then: 카프카 send()가 단 한 번도 호출되지 않았음을 검증
        verify(kafkaTemplate, never()).send(anyString(), any());
    }

    @Test
    @DisplayName("트랜잭션이 정상적으로 커밋되면 카프카로 이벤트가 전송된다.")
    void whenTransactionCommits_thenKafkaEventShouldBePublished() {
        //given
        WalletEventMessage message = WalletEventMessage.builder()
            .orderId("test-order-456")
            .type(MessageType.SETTLEMENT_SUCCESS)
            .build();
        SettlementCompletedEvent event = SettlementCompletedEvent.builder().message(message).build();

        //when
        transactionTemplate.executeWithoutResult(status -> {
            eventPublisher.publishEvent(event);
        });

        //then
        verify(kafkaTemplate, times(1))
            .send(eq(MessageType.SETTLEMENT_SUCCESS.getTopicName()), eq(message));
    }
}
