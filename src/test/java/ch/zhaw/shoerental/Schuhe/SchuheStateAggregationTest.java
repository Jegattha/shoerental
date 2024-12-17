package ch.zhaw.shoerental.Schuhe;
import org.junit.jupiter.api.Test;

import ch.zhaw.shoerental.model.SchuheStateAggregation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import java.util.List;
public class SchuheStateAggregationTest {

      @Test
    public void testConstructorAndGetters() {
        String id = "aggregationId";
        List<String> schuheIds = Arrays.asList("schuheId1", "schuheId2");
        String count = "2";
   
        SchuheStateAggregation aggregation = new SchuheStateAggregation(id, schuheIds, count);

        assertEquals(id, aggregation.getId());
        assertEquals(schuheIds, aggregation.getSchuheIds());
        assertEquals(count, aggregation.getCount());
    }


    @Test
    public void testGettersAfterConstruction() {
        SchuheStateAggregation aggregation = new SchuheStateAggregation("aggregationId", Arrays.asList("schuheId1", "schuheId2"), "2");

        assertEquals("aggregationId", aggregation.getId());
        assertEquals(Arrays.asList("schuheId1", "schuheId2"), aggregation.getSchuheIds());
        assertEquals("2", aggregation.getCount());
    }

    @Test
    public void testDefaultConstructor() {
        SchuheStateAggregation aggregation = new SchuheStateAggregation();

        assertNull(aggregation.getCount());
    }
}