package com.invex.example.jlmb.data.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private MetaDTO meta;
	private Object data;
	
}
