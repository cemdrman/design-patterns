package designpatterns.creational.builder;

import java.util.Objects;

/**
 * Immutable product. It can only be created through {@link Car.Builder}, which validates
 * the required fields and offers sensible defaults for the optional ones.
 */
public final class Car {

    private final String brand;
    private final String model;
    private final String color;
    private final int horsePower;
    private final boolean sunroof;
    private final boolean navigation;

    private Car(Builder builder) {
        this.brand = builder.brand;
        this.model = builder.model;
        this.color = builder.color;
        this.horsePower = builder.horsePower;
        this.sunroof = builder.sunroof;
        this.navigation = builder.navigation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String brand() {
        return brand;
    }

    public String model() {
        return model;
    }

    public String color() {
        return color;
    }

    public int horsePower() {
        return horsePower;
    }

    public boolean hasSunroof() {
        return sunroof;
    }

    public boolean hasNavigation() {
        return navigation;
    }

    @Override
    public String toString() {
        return "%s %s, %s, %d hp, sunroof=%s, navigation=%s"
                .formatted(brand, model, color, horsePower, sunroof, navigation);
    }

    /** Concrete builder that produces a {@link Car}. */
    public static final class Builder implements CarBuilder {

        private String brand;
        private String model;
        private String color = "White";
        private int horsePower = 100;
        private boolean sunroof;
        private boolean navigation;

        private Builder() {
        }

        @Override
        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        @Override
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        @Override
        public Builder color(String color) {
            this.color = color;
            return this;
        }

        @Override
        public Builder horsePower(int horsePower) {
            this.horsePower = horsePower;
            return this;
        }

        @Override
        public Builder sunroof(boolean sunroof) {
            this.sunroof = sunroof;
            return this;
        }

        @Override
        public Builder navigation(boolean navigation) {
            this.navigation = navigation;
            return this;
        }

        public Car build() {
            Objects.requireNonNull(brand, "brand is required");
            Objects.requireNonNull(model, "model is required");
            if (horsePower <= 0) {
                throw new IllegalStateException("horsePower must be positive");
            }
            return new Car(this);
        }
    }
}
