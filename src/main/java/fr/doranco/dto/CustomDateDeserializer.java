package fr.doranco.dto;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    String dateString = jsonParser.getText();
    try {
      return DATE_FORMAT.parse(dateString);
    } catch (ParseException e) {
      throw new IOException("Error parsing date: " + dateString, e);
    }
  }
}
