package designpatterns.behavioral.templatemethod;

import java.util.List;
import java.util.Locale;

/** Concrete class: JSON output, overrides hooks to wrap rows in an array. */
public class JsonReportExporter extends ReportExporter {

    @Override
    protected String header() {
        return "[";
    }

    @Override
    protected String formatRow(Sale sale) {
        return String.format(Locale.ROOT, "  {\"product\": \"%s\", \"quantity\": %d, \"price\": %.2f}",
                sale.product(), sale.quantity(), sale.price());
    }

    @Override
    protected String rowSeparator() {
        return ",\n";
    }

    @Override
    protected String footer(List<Sale> sales) {
        return "]";
    }
}
