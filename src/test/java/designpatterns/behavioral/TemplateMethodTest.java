package designpatterns.behavioral;

import designpatterns.behavioral.templatemethod.CsvReportExporter;
import designpatterns.behavioral.templatemethod.JsonReportExporter;
import designpatterns.behavioral.templatemethod.ReportExporter.Sale;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemplateMethodTest {

    private final List<Sale> sales = List.of(new Sale("Tea", 2, 10), new Sale("Skip", 0, 5));

    @Test
    void sameSkeletonDifferentSteps() {
        assertEquals("product,quantity,price\nTea,2,10.00\nTOTAL,,20.00", new CsvReportExporter().export(sales));
        String json = new JsonReportExporter().export(sales);
        assertTrue(json.startsWith("[") && json.endsWith("]"));
        assertTrue(!json.contains("Skip"));
    }
}
