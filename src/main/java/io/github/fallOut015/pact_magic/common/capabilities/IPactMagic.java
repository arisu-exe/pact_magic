package io.github.fallOut015.pact_magic.common.capabilities;

public interface IPactMagic {
	void tick();
	
	int getAngelKarma();
	int getDemonKarma();
	void setAngelKarma(int angelKarma);
	void setDemonKarma(int demonKarma);
	void spendAngelKarma(int rank);
	void spendDemonKarma(int rank);
	
	AngelInstance getSlottedAngel();
	DemonInstance getSlottedDemon();
	void slotAngel(int ia);
	void slotDemon(int id);

	int getAngelIndex();
	int getDemonIndex();

	String getAngelsSerialized();
	String getDemonsSerialized();
}