package com.invex.example.jlmb.data.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class MetaDTO implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private String status;
    private int statusCode;
    private final String timestamp = LocalDateTime.now().toString();
    private final String transactionID = UUID.randomUUID().toString();
    private List<String> message;
    private boolean rollback;
    
    public void addMessage(String message) {
    	if( this.message == null )
    		this.message = new ArrayList<>();
    	this.message.add(message);
    }
    
}
