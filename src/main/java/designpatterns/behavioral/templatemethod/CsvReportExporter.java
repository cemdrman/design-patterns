package designpatterns.behavioral.templatemethod;

import java.util.List;
import java.util.Locale;

/** Concrete class: CSV output with a total line. */
public class CsvReportExporter extends ReportExporter {

    @Override
    protected String header() {
        return "product,quantity,price";
    }

    @Override
    protected String formatRow(Sale sale) {
        return String.format(Locale.ROOT, "%s,%d,%.2f", sale.product(), sale.quantity(), sale.price());
    }

    @Override
    protected String footer(List<Sale> sales) {
        double total = sales.stream().mapToDouble(sale -> sale.quantity() * sale.price()).sum();
        return String.format(Locale.ROOT, "TOTAL,,%.2f", total);
    }
}
