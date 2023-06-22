package fr.doranco.dto.utilisateur;


import javax.persistence.Transient;
import javax.ws.rs.core.Cookie;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseAuthDto {

  private String message;

  @JsonIgnore
  @Transient
  @XmlTransient
  private Cookie cookieWithJwt;


}
