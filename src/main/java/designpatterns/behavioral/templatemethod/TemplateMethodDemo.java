package designpatterns.behavioral.templatemethod;

import designpatterns.common.Console;
import designpatterns.behavioral.templatemethod.ReportExporter.Sale;

import java.util.List;

public class TemplateMethodDemo {

    public static void main(String[] args) {
        Console.title("Template Method");

        List<Sale> sales = List.of(
                new Sale("Tea", 3, 25.0),
                new Sale("Turkish coffee", 2, 45.5),
                new Sale("Returned item", 0, 99.0),
                new Sale("Simit", 5, 15.0));

        Console.section("CSV");
        Console.line(new CsvReportExporter().export(sales));

        Console.section("JSON");
        Console.line(new JsonReportExporter().export(sales));
    }
}
