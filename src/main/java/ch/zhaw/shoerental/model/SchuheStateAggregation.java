package ch.zhaw.shoerental.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SchuheStateAggregation {
    private String id;
    private List<String> schuheIds;
    private String count;
    
}
