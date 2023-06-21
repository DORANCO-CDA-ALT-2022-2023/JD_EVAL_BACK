package fr.doranco.dto;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd h:mm a z");

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    String formattedDate = DATE_FORMAT.format(date);
    jsonGenerator.writeString(formattedDate);
  }
}
