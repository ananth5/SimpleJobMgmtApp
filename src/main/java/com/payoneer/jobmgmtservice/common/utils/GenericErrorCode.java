package com.payoneer.jobmgmtservice.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GenericErrorCode implements Serializable {

    private static final long serialVersionUID = 4249821152255333401L;

    private static List<String> uniqueCodes = new ArrayList<String>();
    private static Map<String,GenericErrorCode> uniquemapCodes= new HashMap<>();

	private String code;

	private String module;

	private String description;

	
	public static List<String> getAllErrorCodes() {
	    return uniqueCodes;
	}
	
	public static GenericErrorCode getClkMapErrorCodes(String code) {
		return uniquemapCodes.get(code);
	}
	
	private GenericErrorCode(String code,String module, String description) {
		this.code = code;
		this.module = module;
		this.description = description;
		uniqueCodes.add(code);
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static GenericErrorCode create(String code, String module,
			String description) {
		if (uniqueCodes.contains(code)) {
			throw new RuntimeException("Duplicate code:" + code);
		}
		GenericErrorCode clkErrCode =	new GenericErrorCode(code, module, description);
		uniquemapCodes.put(code, clkErrCode);
		return clkErrCode;
	}

	public static void validate(String enumName, String codeStr) {
		if (!codeStr.equals(enumName)) {
			String errMsg = String.format(
					"Name of enum [%s] and error code [%s] must be the same ",
					enumName, codeStr);
			throw new RuntimeException(errMsg);
		}
	}

}
