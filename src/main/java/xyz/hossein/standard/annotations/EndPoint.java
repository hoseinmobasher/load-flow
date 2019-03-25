package xyz.hossein.standard.annotations;

public @interface EndPoint {
    Direction direction() default Direction.IN;

    enum Direction {
        IN,
        OUT
    }
}

