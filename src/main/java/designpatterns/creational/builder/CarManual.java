package designpatterns.creational.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * A completely different product built with the <b>same steps</b>: the user manual of a car.
 * This is what GoF means by "the same construction process can create different representations".
 */
public record CarManual(List<String> pages) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements CarBuilder {

        private final List<String> pages = new ArrayList<>();

        private Builder() {
        }

        @Override
        public Builder brand(String brand) {
            pages.add("Brand: " + brand);
            return this;
        }

        @Override
        public Builder model(String model) {
            pages.add("Model: " + model);
            return this;
        }

        @Override
        public Builder color(String color) {
            pages.add("Paint care for color " + color);
            return this;
        }

        @Override
        public Builder horsePower(int horsePower) {
            pages.add("Engine: " + horsePower + " hp, use 98 octane fuel");
            return this;
        }

        @Override
        public Builder sunroof(boolean sunroof) {
            if (sunroof) {
                pages.add("How to open the sunroof");
            }
            return this;
        }

        @Override
        public Builder navigation(boolean navigation) {
            if (navigation) {
                pages.add("How to update the navigation maps");
            }
            return this;
        }

        public CarManual build() {
            return new CarManual(List.copyOf(pages));
        }
    }
}
