CREATE SEQUENCE credit_card_application_status_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS credit_card_application_status
(
    `id`   INT         NOT NULL DEFAULT NEXT VALUE FOR credit_card_application_status_id_seq PRIMARY KEY,
    `key`  VARCHAR(32) NOT NULL,
    `name` VARCHAR(32) NOT NULL
);

CREATE UNIQUE INDEX idx_unique_credit_card_request_status_key ON credit_card_application_status (`key`);

INSERT INTO credit_card_application_status (`key`, `name`)
VALUES ('STP', 'Stp'),
       ('NEAR_STP', 'Near Stp'),
       ('MANUAL_REVIEW', 'Manual Review'),
       ('REJECTED', 'Rejected');