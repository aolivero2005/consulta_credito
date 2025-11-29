CREATE TABLE IF NOT EXISTS account
(
    id                  SERIAL PRIMARY KEY,
    account_number      BIGINT UNIQUE NOT NULL,
    account_balance     double precision NOT NULL,
    created_date        timestamp default current_timestamp,
    last_modified_date  timestamp
);

CREATE TABLE IF NOT EXISTS transaction
(
    id                  SERIAL PRIMARY KEY,
    account_id          integer NOT NULL REFERENCES account (id) ON DELETE CASCADE,
    payment_method      varchar(1),
    transaction_value   double precision NOT NULL,
    transaction_tax     double precision NOT NULL,
    created_date        timestamp default current_timestamp,
    last_modified_date  timestamp
    );