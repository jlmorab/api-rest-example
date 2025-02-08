package com.invex.example.jlmb.enums;

import com.invex.example.jlmb.data.dto.MetaDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MetaEnum {
	OK			("OK", 200),
    ERROR		("ERROR", 400);
	
	private final String status;
    private final int statusCode;
    
    public MetaDTO getMeta() {
        return MetaDTO
            .builder()
            .status(this.status)
            .statusCode(this.statusCode)
            .build();
    }
}
