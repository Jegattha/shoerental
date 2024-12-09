package ch.zhaw.shoerental.model;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class MieterSchuheAggregationDTO {
    private List<String> schuheId;
    private double preis;
    private String id;
    
}
