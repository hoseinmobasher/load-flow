package xyz.hossein.impl.algorithms;

import xyz.hossein.standard.abstracts.Distributor;
import xyz.hossein.standard.annotations.Algorithm;

@Algorithm(value = "circuit-breaker")
public class CircuitBreakerDistributer implements Distributor {
    @Override
    public void distribute() {

    }
}
