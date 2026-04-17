package com.payment.wallet_service.wallet.repository;

import com.payment.wallet_service.wallet.domain.Wallet;
import java.util.Set;

public interface WalletRepository {

    Set<Wallet> selectWallets(Set<Long> sellerIds);

}
