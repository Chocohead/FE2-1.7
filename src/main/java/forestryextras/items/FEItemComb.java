package forestryextras.items;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.recipes.RecipeManagers;
import forestryextras.main.Main;
import forestryextras.main.init.Tabs;

public class FEItemComb extends Item {
	public FEItemComb(String name, Color primaryColor, Color secondaryColor, ItemStack[] output, int[] chance){
		setUnlocalizedName(Main.alias.toLowerCase() + "." + "comb" + "." + name.toLowerCase());
		setCreativeTab(Tabs.tabMain);

        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		OreDictionary.registerOre("beeComb", this);
		
		if (output != null && chance != null) {
			Map<ItemStack, Float> out = new HashMap<ItemStack, Float>(output.length);
			for (int i = 0; i < output.length; i++) {
				out.put(output[i], ((float)chance[i])/100);
			}
			RecipeManagers.centrifugeManager.addRecipe(10, new ItemStack(this, 1, 0), out);
		}
	}
	public Color primaryColor;
	public Color secondaryColor;
	public IIcon primary;
	public IIcon secondary;
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int renderPass) {
		if(renderPass > 0) {
			return this.primary;
		}
		return this.secondary;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int meta) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass){		
		if(pass > 0) {
			return this.secondaryColor.getRGB();
		}
		return this.primaryColor.getRGB();
	}
	
	@Override
    public void registerIcons(IIconRegister ir) {
		this.primary = ir.registerIcon("forestry:beeCombs.0");
		this.secondary = ir.registerIcon("forestry:beeCombs.1");
	}
}