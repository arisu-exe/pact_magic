package io.github.fallOut015.pact_magic.common.capabilities;

import javax.annotation.Nullable;

import io.github.fallOut015.pact_magic.common.angels.Angel;
import io.github.fallOut015.pact_magic.common.demons.Demon;

public interface IPactMagic {
	void tick();
	
	int getAngelKarma();
	int getDemonKarma();
	void setAngelKarma(int angelKarma);
	void setDemonKarma(int demonKarma);
	void spendAngelKarma(int rank);
	void spendDemonKarma(int rank);
	
	@Nullable Angel getSlottedAngel();
	@Nullable Demon getSlottedDemon();
	void slotAngel(@Nullable Angel slottedAngel);
	void slotDemon(@Nullable Demon slottedDemon);
}