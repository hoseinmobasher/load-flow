package xyz.hossein.standard.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
	String defaultPackage() default "";
	String[] defaultPackages() default {};
}
