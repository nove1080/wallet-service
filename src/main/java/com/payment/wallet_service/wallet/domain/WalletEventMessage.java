package com.payment.wallet_service.wallet.domain;

import lombok.Builder;

@Builder
public record WalletEventMessage(
    MessageType type,
    String orderId
) {

    public enum MessageType {
        SETTLEMENT_SUCCESS;

        public String getTopicName() {
            return this.name().toLowerCase().replace("_", "-");
        }
    }
}
