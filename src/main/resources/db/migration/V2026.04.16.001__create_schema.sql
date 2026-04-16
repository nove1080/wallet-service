CREATE TABLE wallet
(
    wallet_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uk_wallet_member_id UNIQUE (member_id)
);

CREATE TABLE wallet_transaction_history (
    wallet_transaction_history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    transaction_type VARCHAR(255) NOT NULL COMMENT '거래 유형 - 입금, 출금',
    amount DECIMAL(19, 2) NOT NULL COMMENT '거래 금액',
    order_id VARCHAR(255) COMMENT '관련 주문 ID (선택 사항)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_wallet_transaction_history_wallet_id FOREIGN KEY (wallet_id) REFERENCES wallet (wallet_id)
);
