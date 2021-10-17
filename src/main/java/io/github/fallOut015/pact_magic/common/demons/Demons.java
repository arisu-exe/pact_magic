package io.github.fallOut015.pact_magic.common.demons;

public class Demons {
	public static final Demon SATAN;
	public static final Demon BEELZEBUB;
	public static final Demon ASTAROTH; // fraduelent accuser

	public static final Demon ABADDON;
	public static final Demon MAMMON;
	public static final Demon ASMODEUS;

	public static final Demon PYTHO; // Lying
	public static final Demon BELIAL;
	public static final Demon MERIHEM;

	static {
		SATAN = new SatanDemon();
		BEELZEBUB = new BeelzebubDemon();
		ASTAROTH = new AstarothDemon();

		ABADDON = new AbaddonDemon();
		MAMMON = new MammonDemon();
		ASMODEUS = new AsmodeusDemon();

		PYTHO = new PythoDemon();
		BELIAL = new BelialDemon();
		MERIHEM = new MerihemDemon();
	}
}