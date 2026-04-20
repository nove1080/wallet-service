package com.payment.wallet_service.wallet.service;

import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage;
import com.payment.wallet_service.wallet.domain.SettlementCompletedEvent;
import com.payment.wallet_service.wallet.domain.WalletEventMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

@RequiredArgsConstructor
@Component
public class SettlementFacade {

    private final SettlementService settlementService;
    private final TransactionTemplate transactionTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void processSettlement(PaymentConfirmMessage message) {
        transactionTemplate.executeWithoutResult(status -> {
            WalletEventMessage settlementCompletedMessage = settlementService.settle(message);
            applicationEventPublisher.publishEvent(SettlementCompletedEvent.builder().message(settlementCompletedMessage).build());
        });
    }

}
