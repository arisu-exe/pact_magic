package io.github.fallOut015.pact_magic.common.angels;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Angels {
	public static Angel SERAPHIM;
	public static Angel CHERUBIM;
	public static Angel THRONES;

	public static Angel DOMINIONS;
	public static Angel VIRTUES;
	public static Angel POWERS;

	public static Angel PRINCIPALITIES;
	public static Angel ARCHANGELS;
	public static Angel ANGELS;
	
    public static void setup(final FMLCommonSetupEvent event) {
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