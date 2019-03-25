package xyz.hossein;

import xyz.hossein.configuration.Context;
import xyz.hossein.configuration.LoadFlowConfiguration;
import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.abstracts.Element;
import xyz.hossein.standard.annotations.Algorithm;
import xyz.hossein.standard.annotations.Configuration;

@Configuration(defaultPackage = "xyz.hossein")
public class LoadFlowApplication {
    public static void main(String[] args) {
        Context context = LoadFlowConfiguration.run(LoadFlowApplication.class);
        Distributor distributor = (Distributor) context.getElement("random");
        distributor.distribute();
    }
}
