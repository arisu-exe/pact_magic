package io.github.fallOut015.pact_magic.common.angels;

public interface IToggleable {
	public boolean isOn();
	public default boolean isOff() {
		return !isOn();
	}
}