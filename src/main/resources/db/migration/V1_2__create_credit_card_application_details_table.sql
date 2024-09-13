CREATE SEQUENCE credit_card_application_details_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS credit_card_application_details
(
    `id`                     BIGINT         NOT NULL DEFAULT NEXT VALUE FOR credit_card_application_details_id_seq PRIMARY KEY,
    `requested_credit_limit` DECIMAL(15, 2) NOT NULL,
    `bank_statement_url`     VARCHAR(2083)  NOT NULL
);