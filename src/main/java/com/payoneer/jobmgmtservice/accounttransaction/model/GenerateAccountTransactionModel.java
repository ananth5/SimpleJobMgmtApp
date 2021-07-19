package com.payoneer.jobmgmtservice.accounttransaction.model;

import lombok.Data;


/**
 * @author Ananth Shanmugam 
 * Model class for GenerateAccountTransactionModel
 */
@Data
public class GenerateAccountTransactionModel {

	private String TransactionId;
	private String initiationDateTime;
	private String referenceNumber;
	private String transactionCode;
	private String oFIAccountNumber;
	private String oFIAccountType;
	private String oFIAccountName;
	private String rFIAccountNumber;
	private String rFIAccountType;
	private String rFIAccountName;
	private String transactionAmount;
	private String transactionStatus;

	private String rfiBankCode;
	private String rfiBankName;


	
}
