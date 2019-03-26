package xyz.hossein.standard.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EndPoint {
	Direction direction() default Direction.IN;

	enum Direction {
		IN,
		OUT
	}
}

