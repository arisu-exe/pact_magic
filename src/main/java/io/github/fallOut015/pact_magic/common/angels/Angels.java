package io.github.fallOut015.pact_magic.common.angels;

public class Angels {
	public static final Angel SERAPHIM;
	public static final Angel CHERUBIM;
	public static final Angel THRONES;

	public static final Angel DOMINIONS;
	public static final Angel VIRTUES;
	public static final Angel POWERS;

	public static final Angel PRINCIPALITIES;
	public static final Angel ARCHANGELS;
	public static final Angel ANGELS;

	static {
		SERAPHIM = new SeraphimAngel();
		CHERUBIM = new CherubimAngel();
		THRONES = new ThronesAngel();

		DOMINIONS = new DominionsAngel();
		VIRTUES = new VirtuesAngel();
		POWERS = new PowersAngel();

		PRINCIPALITIES = new PrincipalitiesAngel();
		ARCHANGELS = new ArchangelsAngel();
		ANGELS = new AngelsAngel();
	}
}