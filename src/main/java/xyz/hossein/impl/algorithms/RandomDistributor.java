package xyz.hossein.impl.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Algorithm;

@Algorithm(value = "random")
public class RandomDistributor implements Distributor {
	private static final Logger logger = LoggerFactory.getLogger(RandomDistributor.class);

	@Override
	public void distribute() {
		logger.info("Random distributor ready to use.");
	}
}
