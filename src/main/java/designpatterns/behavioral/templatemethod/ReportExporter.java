package designpatterns.behavioral.templatemethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class defining the <b>skeleton</b> of the algorithm in {@link #export(List)}.
 * The template method is {@code final}, so subclasses can change some steps
 * but never the order of the steps.
 */
public abstract class ReportExporter {

    public record Sale(String product, int quantity, double price) {
    }

    /** The template method. */
    public final String export(List<Sale> sales) {
        List<Sale> valid = sales.stream().filter(sale -> sale.quantity() > 0).toList();
        StringBuilder out = new StringBuilder();
        if (includeHeader()) {
            out.append(header()).append('\n');
        }
        out.append(valid.stream().map(this::formatRow).collect(Collectors.joining(rowSeparator())));
        String footer = footer(valid);
        if (!footer.isEmpty()) {
            out.append('\n').append(footer);
        }
        return out.toString();
    }

    // Primitive operations: every subclass must implement them.
    protected abstract String header();

    protected abstract String formatRow(Sale sale);

    // Hooks: have a default behaviour, subclasses may override them.
    protected boolean includeHeader() {
        return true;
    }

    protected String rowSeparator() {
        return "\n";
    }

    protected String footer(List<Sale> sales) {
        return "";
    }
}
