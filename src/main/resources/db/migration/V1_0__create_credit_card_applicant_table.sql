CREATE SEQUENCE credit_card_applicant_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS credit_card_applicant
(
    `id`                       BIGINT         NOT NULL DEFAULT NEXT VALUE FOR credit_card_applicant_id_seq PRIMARY KEY,
    `personal_identity_number` VARCHAR(50)    NOT NULL,
    `name`                     VARCHAR(255)   NOT NULL,
    `mobile_number`            VARCHAR(20)    NOT NULL,
    `nationality`              VARCHAR(50)    NOT NULL,
    `residential_address`      VARCHAR(255)   NOT NULL,
    `annual_income`            DECIMAL(15, 2) NOT NULL,
    `employer`                 VARCHAR(255)   NOT NULL,
    `employment_status`        VARCHAR(255)   NOT NULL
);