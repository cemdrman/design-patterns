package designpatterns.spring.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

/**
 * Singleton bean (the default scope): one shared, stateless instance – the Singleton pattern
 * without {@code getInstance()}. It uses {@link ObjectProvider} as a <b>factory</b> to get a fresh
 * prototype {@link ReportBuilder} for every report.
 */
@Service
public class ReportService {

    private final ObjectProvider<ReportBuilder> builders;

    public ReportService(ObjectProvider<ReportBuilder> builders) {
        this.builders = builders;
    }

    public String dailyReport(String... lines) {
        ReportBuilder builder = builders.getObject();
        for (String line : lines) {
            builder.line(line);
        }
        return builder.build();
    }
}
