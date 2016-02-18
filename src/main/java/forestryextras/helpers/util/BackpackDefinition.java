package forestryextras.helpers.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import forestry.api.storage.IBackpackDefinition;
//import forestry.core.config.ForestryItem;
import forestry.plugins.PluginApiculture;

public class BackpackDefinition implements IBackpackDefinition{

	public BackpackDefinition(String key, String name, int primaryColor){
		cont = new ArrayList<ItemStack>();
		this.key = key;
		this.name = name;
		this.primaryColor = primaryColor;
		
		checkForContent();
	}
	String key;
	String name;
	int primaryColor;
	ArrayList<ItemStack> cont;
	
	public void checkForContent()
	{
		if(this.name == "Frame Backpack"){
			cont.add(new ItemStack(PluginApiculture.items.frameImpregnated));
			cont.add(new ItemStack(PluginApiculture.items.frameProven));
			cont.add(new ItemStack(PluginApiculture.items.frameUntreated));
			
			for(ItemStack stack : OreDictionary.getOres("frameForestry"))
				cont.add(stack);
		}
	}
	
	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public int getPrimaryColour() {
		return this.primaryColor;
	}

	@Override
	public int getSecondaryColour() {
		return 0xFFFFFF;
	}

	@Override
	public void addValidItem(ItemStack validItem) {
		if(!this.cont.contains(validItem))
			this.cont.add(validItem);
	}

	@Override
	public void addValidItems(List<ItemStack> validItems) {
		for (ItemStack is : validItems) {
			if(!cont.contains(is))
				cont.add(is);
		}
	}

	@Override
	public boolean isValidItem(ItemStack itemstack) {
		return this.cont.contains(itemstack);
	}

	@Override
	public String getName(ItemStack backpack) {
		return backpack.getDisplayName();
	}

}