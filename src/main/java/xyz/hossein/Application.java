package xyz.hossein;

import xyz.hossein.configuration.Configuration;
import xyz.hossein.configuration.Context;
import xyz.hossein.example.DistributionTest;
import xyz.hossein.standard.annotations.Pojo;

@xyz.hossein.standard.annotations.Configuration(defaultPackage = "xyz.hossein")
public class Application {
	public static void main(String[] args) {
		Context context = Configuration.run(Application.class);
		DistributionTest test = (DistributionTest) context.getElement(Pojo.class, "distribution-test");
		test.distribute();
	}
}
