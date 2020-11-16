package io.github.fallOut015.pact_magic.common.capabilities;

import java.util.concurrent.Callable;

public class PactMagicCallable implements Callable<IPactMagic> {
	@Override
	public IPactMagic call() throws Exception {
		return new PactMagic(null);
	}
}