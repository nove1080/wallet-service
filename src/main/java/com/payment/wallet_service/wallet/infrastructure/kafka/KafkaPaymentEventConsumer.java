package com.payment.wallet_service.wallet.infrastructure.kafka;

import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage;
import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage.MessageType;
import com.payment.wallet_service.wallet.service.SettlementFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaPaymentEventConsumer {

    private final SettlementFacade settlementFacade;

    @KafkaListener(
        topics = MessageType.PAYMENT_CONFIRM_SUCCESS,
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "paymentConfirmKafkaListenerContainerFactory"
    )
    public void consumePaymentConfirmMessage(PaymentConfirmMessage message) {
        log.info("received topic: {}, message: {}", MessageType.PAYMENT_CONFIRM_SUCCESS, message);
        settlementFacade.processSettlement(message);
    }

}
