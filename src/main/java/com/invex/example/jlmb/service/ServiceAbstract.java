package com.invex.example.jlmb.service;

import java.util.function.Supplier;

import org.slf4j.Logger;

import com.invex.example.jlmb.data.dto.MetaDTO;
import com.invex.example.jlmb.data.dto.WebResponseDTO;
import com.invex.example.jlmb.enums.MetaEnum;
import com.invex.example.jlmb.utils.ObjectUtils;

import jakarta.servlet.http.HttpServletResponse;

public abstract class ServiceAbstract {
	
	protected WebResponseDTO executeFlow(HttpServletResponse response, Logger log, Supplier<Object> logic) {
		log.trace(">> {}()", getMethodInvocation());
		try {
			Object data = logic.get();
			traceLeavingMethod( log, data );
			return responseOK( response, data );
		} catch( Exception e ) {
			log.error("An error occurred while retrieving data", e);
			return responseError( response, e );
		}
	}
	
	protected WebResponseDTO responseOK( HttpServletResponse response ) {
		return response( response, MetaEnum.OK );
	}
	
	protected WebResponseDTO responseOK( HttpServletResponse response, Object data ) {
		return responseWithData( response, MetaEnum.OK, data );
	}
	
	protected WebResponseDTO responseError( HttpServletResponse response, Exception e ) {
		WebResponseDTO webResponse = response( response, MetaEnum.ERROR );
		webResponse.getMeta().addMessage( e.getMessage() );
		return webResponse;
	}
	
	protected WebResponseDTO responseWithData( HttpServletResponse response, MetaEnum meta, Object data ) {
		WebResponseDTO webResponse = response( response, meta );
		webResponse.setData(data);
		return webResponse;
	}
	
	protected WebResponseDTO response( HttpServletResponse response, MetaEnum meta ) {
		MetaDTO metaDTO = meta.getMeta();
		response.setStatus( metaDTO.getStatusCode() );
		return WebResponseDTO.builder()
				.meta( metaDTO )
				.build();
	}
	
	private void traceLeavingMethod( Logger log, Object object ) {
		if (log.isTraceEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("<< %s() < Returning ", getMethodInvocation()));
			if (object != null) {
				sb.append( object.getClass().getName() + " = " );
				sb.append( ObjectUtils.getJsonString(object) );
			} else {
				sb.append("null");
			}
			log.trace(sb.toString());
		}
	}
	
	private String getMethodInvocation() {
		return StackWalker.getInstance()
				.walk(frames -> frames.skip(1)
						.filter(frame -> !frame.getClassName().equals(ServiceAbstract.class.getName()))
						.findFirst()
						.map(frame -> String.format("%s.%s", frame.getClassName(), frame.getMethodName()))
						.orElse("Method not available"));
	}
	
}
