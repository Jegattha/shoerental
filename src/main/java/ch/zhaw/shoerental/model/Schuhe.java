package ch.zhaw.shoerental.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("schuhe")
public class Schuhe {

    @Id
    private String schuheId; // Automatisch generiert von MongoDB

    @NonNull
    private String marke;
    @NonNull
    private Double preis; 
    private SchuheState schuheState = SchuheState.VERFUEGBAR; // Standardwert
    @NonNull
    private SchuheType schuheType;
    @NonNull
    private String groesse;
    @NonNull
    private String schuheFarbe;
    @NonNull
    private String schuheBeschreibung;

    private String vermieterId;
    private String mieterId;

    private Date mietdauerVon;
    private Date mietdauerBis;
    private Double totalPreis;

     /**
     * Vollständiger Konstruktor für die wichtigsten Felder.
     * Dieser Konstruktor deckt die Felder ab, die im DTO vorhanden sind.
     */
    public Schuhe(@NonNull String marke, 
                  @NonNull Double preis, 
                  @NonNull SchuheType schuheType, 
                  @NonNull String groesse, 
                  @NonNull String schuheFarbe, 
                  @NonNull String schuheBeschreibung,
                  String vermieterId   
                
                   ) {
        this.marke = marke;
        this.preis = preis;
        this.schuheType = schuheType;
        this.groesse = groesse;
        this.schuheFarbe = schuheFarbe;
        this.schuheBeschreibung = schuheBeschreibung;
        this.vermieterId = vermieterId;
      

    }

 

}
