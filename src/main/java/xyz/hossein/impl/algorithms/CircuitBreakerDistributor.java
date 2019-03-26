package xyz.hossein.impl.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Algorithm;

@Algorithm(value = "circuit-breaker")
public class CircuitBreakerDistributor implements Distributor {
	private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerDistributor.class);

	@Override
	public void distribute() {
		logger.info("CircuitBreaker distributor ready to use.");
	}
}
