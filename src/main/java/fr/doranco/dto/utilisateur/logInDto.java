package fr.doranco.dto.utilisateur;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class logInDto {

  @JsonProperty("email")
  @NotNull
  @NotBlank
  @Email(message = "Email n'est pas correcte")
  private String email;

  @JsonProperty("password")
  @NotNull
  @NotBlank
  private String password;

}
