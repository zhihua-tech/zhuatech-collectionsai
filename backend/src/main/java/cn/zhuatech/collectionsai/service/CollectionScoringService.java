/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.collectionsai.service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 对逾期、金额、承诺与争议信号进行可解释催收分级。 */
@Service
public class CollectionScoringService {
    public Result score(Request request) {
        int risk = Math.min(45, request.overdueDays());
        if (request.amount().compareTo(new BigDecimal("100000")) >= 0) risk += 20;
        else if (request.amount().compareTo(new BigDecimal("30000")) >= 0) risk += 10;
        risk += Math.min(24, request.brokenPromiseCount() * 8);
        if (request.lastContactDays() > 14) risk += 12;
        if (request.disputeOpen()) risk += 18;
        if (request.strategicCustomer()) risk = Math.max(0, risk - 8);
        risk = Math.min(100, risk);
        String tier = risk >= 75 ? "CRITICAL" : risk >= 50 ? "HIGH" : risk >= 25 ? "MEDIUM" : "LOW";
        String strategy = request.disputeOpen() ? "DISPUTE_RESOLUTION" : risk >= 75 ? "MANAGER_NEGOTIATION" : risk >= 50 ? "PROMISE_TO_PAY" : "FRIENDLY_REMINDER";
        List<String> actions = new ArrayList<>();
        if (request.disputeOpen()) actions.add("暂停自动触达，先由业务与法务确认争议责任");
        if (request.brokenPromiseCount() > 0) actions.add("核验历史付款承诺并更新可信日期");
        if (request.lastContactDays() > 14) actions.add("当天完成一次人工有效联系");
        if (request.strategicCustomer()) actions.add("由客户负责人参与沟通，避免影响合作关系");
        if (actions.isEmpty()) actions.add("发送到期提醒并记录客户反馈");
        return new Result(request.invoiceNo(), risk, tier, strategy,
            risk >= 75 ? "TODAY" : risk >= 50 ? "WITHIN_2_DAYS" : "THIS_WEEK",
            request.disputeOpen() || request.amount().compareTo(new BigDecimal("200000")) >= 0,
            actions, request.amount());
    }

    public record Request(@NotBlank String invoiceNo, @Min(0) int overdueDays,
                          @DecimalMin("0.01") BigDecimal amount,
                          @Min(0) @Max(10) int brokenPromiseCount,
                          @Min(0) int lastContactDays,
                          boolean disputeOpen, boolean strategicCustomer) {}
    public record Result(String invoiceNo, int riskScore, String riskTier, String strategy,
                         String nextTouch, boolean humanApprovalRequired,
                         List<String> actions, BigDecimal exposureAmount) {}
}
