package designpatterns.spring.scope;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Stateful, so it must <b>not</b> be a singleton: prototype scope gives a new instance every time
 * it is requested from the container.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportBuilder {

    private final List<String> lines = new ArrayList<>();

    public ReportBuilder line(String line) {
        lines.add(line);
        return this;
    }

    public String build() {
        return String.join(" | ", lines);
    }
}
