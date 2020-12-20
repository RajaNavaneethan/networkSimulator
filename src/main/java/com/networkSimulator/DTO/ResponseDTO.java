package com.networkSimulator.DTO;

import org.json.JSONObject;

public class ResponseDTO {
	private String mesg ;
	private int httpResponse;
	public int getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(int httpResponse) {
		this.httpResponse = httpResponse;
	}

	public String getMesg() {
		return mesg;
	}

	public void setMesg(String mesg) {
		this.mesg = mesg;
	}

	@Override
	public String toString() {
		return "ResponseDTO [mesg=" + mesg + ", httpResponse=" + httpResponse + "]";
	}

	
}
