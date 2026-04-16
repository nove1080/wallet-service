package com.payment.wallet_service.wallet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    CREDIT("대변"),
    DEBIT("차변");

    private final String description;
}
