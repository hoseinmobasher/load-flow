package xyz.hossein.impl.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Algorithm;

@Algorithm(value = "topic")
public class TopicDistributor implements Distributor {
	private static final Logger logger = LoggerFactory.getLogger(TopicDistributor.class);

	@Override
	public void distribute() {
		logger.info("Topic distributor ready to use.");
	}
}
