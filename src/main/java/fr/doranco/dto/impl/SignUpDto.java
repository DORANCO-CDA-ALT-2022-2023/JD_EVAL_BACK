package fr.doranco.dto.impl;

import java.util.Date;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpDto {

  @JsonProperty("email")
  @NotNull
  private String email;

  @JsonProperty("password")
  @NotNull
  private String password;

  private String adresse;

  @JsonProperty("date")
  @JsonFormat(pattern = "dd-MM-yyyy")
  private Date date;

}
