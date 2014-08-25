package forestryextras.items.bees.effects;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.genetics.IEffectData;
import forestryextras.helpers.EffectHelper;

public class EffectWither extends BeeEffect{
	public EffectWither(String name) {
		super(name);
		this.name = name;
	}
	
	@Override
	public IEffectData doEffect(IBeeGenome genome, IEffectData storedData,
			IBeeHousing housing, World world, int x, int y, int z){
		ArrayList<EntityPlayer> players = EffectHelper.getClosePlayers(world, x, y, z, 2D);

		for(EntityPlayer player : players){
			player.addPotionEffect(new PotionEffect(Potion.wither.id, 40));
		}
		
		return null;
	}
}