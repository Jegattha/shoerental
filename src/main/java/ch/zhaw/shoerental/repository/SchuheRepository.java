package ch.zhaw.shoerental.repository;
import ch.zhaw.shoerental.model.Schuhe;
import ch.zhaw.shoerental.model.SchuheStateAggregation;
import ch.zhaw.shoerental.model.MieterSchuheAggregationDTO;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SchuheRepository extends MongoRepository<Schuhe,String>{
    List<Schuhe>findByMarke(String marke);
    @Aggregation("{'$group':{_id:'$schuheState',schuheIds:{'$push':'$_id'},count:{'$count':{}}}}")
      List<SchuheStateAggregation> getSchuheStateAggregations();
      //aggregieren von vermietete Schuhen und verfügbare Schuhen
      @Aggregation("{$group: {_id: '$mieterId', schuheId: {$push: '$_id'}, totalPrices: {$sum: '$preis'}}}")
List<MieterSchuheAggregationDTO> getMieterSchuheAggregation();
@Aggregation("{$match: {schuheState: 'VERFUEGBAR'}}," +
"{$group: {_id: null, totalPrices: {$sum: '$preis'}}}," +
"{$project: {_id: 0, totalPrices: 1}}")
Double getGesamtpreisVerfuegbareSchuhe();

List<Schuhe> findByVermieterId(String vermieterId);


}



  
