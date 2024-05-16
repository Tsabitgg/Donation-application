package com.ict.careus.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class CampaignTransactionsHistoryResponse {
    private long id;
    private String username;
    private double transactionAmount;
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;
}
