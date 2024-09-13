CREATE SEQUENCE credit_card_verification_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS credit_card_verification
(
    `id`                        BIGINT  NOT NULL DEFAULT NEXT VALUE FOR credit_card_verification_id_seq PRIMARY KEY,
    `identity_verified`         TINYINT NOT NULL DEFAULT 0,
    `compliance_check_passed`   TINYINT NOT NULL DEFAULT 0,
    `employment_verified`       TINYINT NOT NULL DEFAULT 0,
    `risk_evaluation_score`     DECIMAL NOT NULL DEFAULT 0,
    `behavioral_analysis_score` DECIMAL NOT NULL DEFAULT 0
);