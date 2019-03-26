package xyz.hossein.example;

import xyz.hossein.impl.algorithms.RandomDistributor;
import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Pojo;
import xyz.hossein.standard.annotations.Wire;

import java.util.List;

@Pojo("distribution-test")
public class DistributionTest {
	@Wire
	private RandomDistributor distributor;

	@Wire(value = "round-robin")
	private Distributor distributor2;

	@Wire
	private List<Distributor> distributors;

	public void distribute() {
		distributor.distribute();

		for (Distributor distributor: distributors) {
			distributor.distribute();
		}

		distributor2.distribute();
	}
}
