package com.payment.wallet_service.wallet.service;

import com.payment.wallet_service.wallet.domain.PaymentConfirmMessage;
import com.payment.wallet_service.wallet.domain.PaymentOrder;
import com.payment.wallet_service.wallet.domain.Wallet;
import com.payment.wallet_service.wallet.domain.WalletEventMessage;
import com.payment.wallet_service.wallet.domain.WalletEventMessage.MessageType;
import com.payment.wallet_service.wallet.repository.WalletRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SettlementService {

    private final WalletRepository walletRepository;
    private final DuplicateMessageFilter duplicateMessageFilter;

    public WalletEventMessage settle(PaymentConfirmMessage confirmMessage) {
        if (duplicateMessageFilter.isAlreadyProcessed(confirmMessage)) {
            return createWalletEventMessage(confirmMessage, MessageType.SETTLEMENT_SUCCESS);
        }

        List<Wallet> settledWallets = doSettle(confirmMessage);
        walletRepository.saveAll(settledWallets);

        return createWalletEventMessage(confirmMessage, MessageType.SETTLEMENT_SUCCESS);
    }

    private List<Wallet> doSettle(PaymentConfirmMessage confirmMessage) {
        Map<Long, List<PaymentOrder>> paymentOrderMap = confirmMessage.paymentOrders().stream()
            .collect(Collectors.groupingBy(PaymentOrder::sellerId));

        Set<Wallet> wallets = walletRepository.selectWallets(confirmMessage.getSellerIds());

        return wallets.stream()
            .map(it -> {
                List<PaymentOrder> orders = paymentOrderMap.getOrDefault(it.memberId(), List.of());
                return it.settle(confirmMessage.orderId(), orders);
            }).toList();
    }

    private static WalletEventMessage createWalletEventMessage(PaymentConfirmMessage confirmMessage, MessageType messageType) {
        return WalletEventMessage.builder()
            .type(messageType)
            .orderId(confirmMessage.orderId())
            .build();
    }

}
