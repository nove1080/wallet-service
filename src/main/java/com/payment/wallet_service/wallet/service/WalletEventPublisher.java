package com.payment.wallet_service.wallet.service;

import com.payment.wallet_service.wallet.domain.SettlementCompletedEvent;

public interface WalletEventPublisher {

    void publishSettlementCompletedEvent(SettlementCompletedEvent event);

}
