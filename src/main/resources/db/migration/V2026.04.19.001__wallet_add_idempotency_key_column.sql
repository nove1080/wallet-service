ALTER TABLE wallet_transaction
    ADD COLUMN idempotency_key VARCHAR(255),
    ADD CONSTRAINT uk_wallet_idempotency_key UNIQUE (idempotency_key);
