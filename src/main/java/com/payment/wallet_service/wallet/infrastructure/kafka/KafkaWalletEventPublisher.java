package com.payment.wallet_service.wallet.infrastructure.kafka;

import com.payment.wallet_service.wallet.domain.SettlementCompletedEvent;
import com.payment.wallet_service.wallet.domain.WalletEventMessage.MessageType;
import com.payment.wallet_service.wallet.service.WalletEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaWalletEventPublisher implements WalletEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishSettlementCompletedEvent(SettlementCompletedEvent event) {
        log.info("Publishing settlement completed event to Kafka: {}", event);
        kafkaTemplate.send(MessageType.SETTLEMENT_SUCCESS.getTopicName(), event.message());
    }
}
