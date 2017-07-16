package com.employee.utils;

import javax.ws.rs.ext.ContextResolver;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Produces;
import java.text.SimpleDateFormat;

@Provider
@Produces("application/json")
public class JsonDateConfigurator implements ContextResolver<ObjectMapper> {

	private ObjectMapper mapper = new ObjectMapper();

	public JsonDateConfigurator() {
		SerializationConfig serConfig = mapper.getSerializationConfig();
		serConfig.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
		deserializationConfig.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return mapper;
	}

}
