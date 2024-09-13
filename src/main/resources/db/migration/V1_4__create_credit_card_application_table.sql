CREATE SEQUENCE credit_card_application_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS credit_card_application
(
    `id`                     BIGINT       NOT NULL DEFAULT NEXT VALUE FOR credit_card_application_id_seq PRIMARY KEY,
    `applicant_id`           BIGINT       NOT NULL,
    `status_id`              INT          NOT NULL,
    `verification_id`        BIGINT       NOT NULL,
    `application_details_id` BIGINT       NOT NULL,
    `date_created`           TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `date_updated`           TIMESTAMP(3),
    FOREIGN KEY (`applicant_id`) REFERENCES credit_card_applicant (`id`),
    FOREIGN KEY (`status_id`) REFERENCES credit_card_application_status (`id`),
    FOREIGN KEY (`verification_id`) REFERENCES credit_card_verification (`id`),
    FOREIGN KEY (`application_details_id`) REFERENCES credit_card_application_details (`id`)
);