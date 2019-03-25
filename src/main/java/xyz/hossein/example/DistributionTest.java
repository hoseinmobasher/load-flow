package xyz.hossein.example;

import xyz.hossein.impl.algorithms.RandomDistributor;
import xyz.hossein.standard.annotations.Pojo;
import xyz.hossein.standard.annotations.Wire;

@Pojo
public class DistributionTest {
    @Wire
    private RandomDistributor distributor;

    public void distribute() {
        distributor.distribute();
    }
}
