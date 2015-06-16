package forestryextras.items.bees.flowers;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import vazkii.botania.common.block.BlockSpecialFlower;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IFlower;
import forestry.api.genetics.IIndividual;

public class FlowerBotaniaAdvanced extends FlowerProvider{
	public FlowerBotaniaAdvanced(String name){
		super(name);
		this.name = name;
	    AlleleManager.alleleRegistry.registerAllele(this);
	}
	
	@Override
	public boolean isAcceptedFlower(World world, IIndividual individual, int x, int y, int z, Block block){
		return block instanceof BlockSpecialFlower;
	}
	
	@Override
	public List<IFlower> getFlowers()
	{
		return null;
	}
}