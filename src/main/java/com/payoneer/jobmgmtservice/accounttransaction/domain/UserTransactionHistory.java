package com.payoneer.jobmgmtservice.accounttransaction.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ananth Shanmugam 
 * Entity class for generate account transaction job
 */
@Entity
@Table(name = "USERTRANSACTIONHISTORY")
@NoArgsConstructor
@Data
public class UserTransactionHistory {

    @Id
    @Column(name = "TRANSACTIONID", nullable = false)
    private long transactionId;

    @Column(name = "REFNO", nullable = true, length = 20)
    private String refNo;

    @Column(name = "CHANNEL", nullable = false, length = 5)
    private String channel;

    @Column(name = "USERID", nullable = false, length = 32)
    private String userId;

    @Column(name = "TRANSACTIONTYPE", nullable = false, length = 35)
    private String transactionType;

    @Column(name = "FROMACCNO", nullable = false, length = 20)
    private String fromAccNo;

    @Column(name = "FROMACCTYPE", nullable = false, length = 5)
    private String fromAccType;

    @Column(name = "FROMACCNAME", nullable = false, length = 50)
    private String fromAccName;

    @Column(name = "TOREF", nullable = false, length = 50)
    private String toRef;

    @Column(name = "TOTYPE", nullable = false, length = 100)
    private String toType;

    @Column(name = "AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "STATUS", nullable = false, length = 20)
    private String status;

    @Column(name = "TOTALDEBITAMT", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalDebitAmt;

    @Column(name = "EXCEPTIONID", nullable = true, length = 30)
    private String exceptionId;

    @Column(name = "EXCEPTIONMSG", nullable = true, length = 255)
    private String exceptionMsg;

    @Column(name = "TRXTIMESTAMP", nullable = false)
    private Timestamp trxTimestamp;

    @Column(name = "MODULEID", nullable = true, length = 20)
    private String moduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USERID", insertable = false, updatable = false, referencedColumnName = "USERID")
    private ECUser ecuser;
    
 
    
}
