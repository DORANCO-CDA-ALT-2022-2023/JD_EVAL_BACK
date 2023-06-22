package fr.doranco.dto.utilisateur;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
  @NotBlank
  @Email(message = "Email n'est pas correcte")
  private String email;

  @JsonProperty("password")
  @NotNull
  @NotBlank
  private String password;

  @JsonProperty("adresse")
  private String adresse;

  @JsonProperty("date")
  private Date date;

}
