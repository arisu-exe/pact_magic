package io.github.fallOut015.pact_magic.common.demons;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class Demons {
	public static Demon SATAN;
	public static Demon BEELZEBUB;
	public static Demon ASTAROTH; // fraduelent accuser

	public static Demon ABADDON;
	public static Demon MAMMON;
	public static Demon ASMODEUS;

	public static Demon PYTHO; // Lying
	public static Demon BELIAL;
	public static Demon MERIHEM;
	
	public static void setup(final FMLCommonSetupEvent event) {
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