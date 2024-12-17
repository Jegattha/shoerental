package ch.zhaw.shoerental.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SchuheCreateDTO {
    private String marke;
    private Double preis;
    private SchuheType schuheType;
    private String groesse;
    private String schuheFarbe;
    private String schuheBeschreibung;
    private String vermieterId;
   
    
}



