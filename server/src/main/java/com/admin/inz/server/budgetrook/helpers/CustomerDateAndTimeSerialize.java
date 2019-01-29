package com.admin.inz.server.budgetrook.helpers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomerDateAndTimeSerialize extends JsonSerializer<Date>{

    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss");

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
        try {
    		String formattedDate = dateFormat.format(value);
    		gen.writeString(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
