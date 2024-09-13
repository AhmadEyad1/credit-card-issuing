package com.credit.infrastructure.client.riskEvaluation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskEvaluationResponse {

    private double score;
}
