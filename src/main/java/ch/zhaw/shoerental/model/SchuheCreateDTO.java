package ch.zhaw.shoerental.model;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SchuheCreateDTO {
    private String marke;
    private Double preis;
    private SchuheType schuheType;
    private String groesse;
    private String schuheFarbe;
    private String schuheBeschreibung;
    private String vermieterId;
    
}



