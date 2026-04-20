package com.payment.wallet_service.wallet.domain;

import lombok.Builder;

@Builder
public record SettlementCompletedEvent(
    WalletEventMessage message
) {

}
