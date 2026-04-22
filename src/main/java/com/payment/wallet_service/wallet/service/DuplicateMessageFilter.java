package com.payment.wallet_service.wallet.service;

import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage;
import com.payment.wallet_service.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DuplicateMessageFilter {

    private final WalletRepository walletRepository;

    public boolean isAlreadyProcessed(PaymentConfirmMessage message) {
        return walletRepository.existsSettlement(message.orderId());
    }

}
