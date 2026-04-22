package com.payment.wallet_service.wallet.infrastructure.kafka;

import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage;
import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage.MessageType;
import com.payment.wallet_service.wallet.domain.WalletEventMessage;
import com.payment.wallet_service.wallet.service.SettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaPaymentEventConsumer {

    private final SettlementService settlementService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
        topics = MessageType.PAYMENT_CONFIRM_SUCCESS,
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "paymentConfirmKafkaListenerContainerFactory"
    )
    public void consumePaymentConfirmMessage(PaymentConfirmMessage message) {
        log.info("Received topic: {}, message: {}", MessageType.PAYMENT_CONFIRM_SUCCESS, message);
        WalletEventMessage settlementCompletedMessage = settlementService.settle(message);
        kafkaTemplate.send(WalletEventMessage.MessageType.SETTLEMENT_SUCCESS.getTopicName(), settlementCompletedMessage);
    }

}
