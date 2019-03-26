package xyz.hossein.impl.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Algorithm;

@Algorithm(value = "round-robin")
public class RoundRobinDistributor implements Distributor {
	private static final Logger logger = LoggerFactory.getLogger(RoundRobinDistributor.class);

	@Override
	public void distribute() {
		logger.info("RoundRobin distributor ready to use.");
	}
}
