package designpatterns.refactoring.templatemethod;

import designpatterns.behavioral.templatemethod.ReportExporter.Sale;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * BEFORE Template Method – copy-pasted algorithms.
 * <p>Both methods filter, add a header, join rows and add a footer. A change in a shared step
 * (e.g. "skip refunds too") must be made in every copy – and one copy is always forgotten.
 *
 * @see designpatterns.behavioral.templatemethod.ReportExporter the refactored version
 */
public class ReportsBefore {

    public String toCsv(List<Sale> sales) {
        List<Sale> valid = sales.stream().filter(sale -> sale.quantity() > 0).toList();
        StringBuilder out = new StringBuilder("product,quantity,price\n");
        out.append(valid.stream()
                .map(s -> String.format(Locale.ROOT, "%s,%d,%.2f", s.product(), s.quantity(), s.price()))
                .collect(Collectors.joining("\n")));
        double total = valid.stream().mapToDouble(s -> s.quantity() * s.price()).sum();
        out.append('\n').append(String.format(Locale.ROOT, "TOTAL,,%.2f", total));
        return out.toString();
    }

    public String toJson(List<Sale> sales) {
        List<Sale> valid = sales.stream().filter(sale -> sale.quantity() > 0).toList();
        StringBuilder out = new StringBuilder("[\n");
        out.append(valid.stream()
                .map(s -> String.format(Locale.ROOT, "  {\"product\": \"%s\", \"quantity\": %d, \"price\": %.2f}",
                        s.product(), s.quantity(), s.price()))
                .collect(Collectors.joining(",\n")));
        out.append("\n]");
        return out.toString();
    }
}
