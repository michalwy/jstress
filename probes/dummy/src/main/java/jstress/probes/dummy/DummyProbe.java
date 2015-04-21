package jstress.probes.dummy;

import jstress.core.Probe;

public class DummyProbe extends Probe {

	public static void main(String[] args) {
		DummyProbe probe = new DummyProbe();
		
		probe.run();
	}

}
