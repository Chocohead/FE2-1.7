package forestryextras.items;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.recipes.RecipeManagers;
import forestryextras.main.Main;
import forestryextras.main.init.FEItems;
import forestryextras.main.init.Tabs;

public final class FEItemFrame extends Item implements IHiveFrame {

    public FEItemFrame(int durability, boolean isHelish, boolean isSimulated, boolean isSelfLighted, boolean isSealed,
    		float frameDecay, float floweringMod, float productionMod, float lifespanMod,
    		float mutationMod, float territoryMod, String itemName, String oreDictName, String textureName, int frameColor,
    		ItemStack bindingMaterial, ItemStack frameMaterial, boolean easyRecipe, FluidStack recipeFluid, int creationTime){
		setUnlocalizedName(Main.alias.toLowerCase() + "." + "frame" + "." + itemName);
        setCreativeTab(Tabs.tabFrames);
        setMaxStackSize(1);
        setMaxDamage(durability);
        
        uses = durability;
        helish = isHelish;
        simulated = isSimulated;
        selflighted = isSelfLighted;
        sealed = isSealed;
        decay = frameDecay;
        flowering = floweringMod;
        productionmodifier = productionMod;
        lifespanmodifier = lifespanMod;
        mutationmodifier = mutationMod;
        territorymodifier = territoryMod;        
        
        name = itemName;
        oreDict = oreDictName;
        texture = textureName;
        color = frameColor;
        
        frameMat = frameMaterial;
        bindingMat = bindingMaterial;
        easyRec = easyRecipe;
        recFluid = recipeFluid;
        createTime = creationTime;
        
    	recipe(easyRec);
		GameRegistry.registerItem(this, this.getUnlocalizedName());
		OreDictionary.registerOre(oreDict, this); 
		OreDictionary.registerOre("frameForestry", this); 
		FEItems.list_backpack_frame.add(new ItemStack(this));
    }
    IIcon primary;
    IIcon secondary;
    boolean helish;
    boolean simulated;
    boolean selflighted;
    boolean sealed;
    float decay;
    float flowering;
    float productionmodifier;
    float lifespanmodifier;
    float mutationmodifier;
    float territorymodifier;
	String name;
	String oreDict;
	int color;
    String texture;
    ItemStack bindingMat;
    ItemStack frameMat;
    boolean easyRec;
    FluidStack recFluid;
    int createTime;
    int uses;
    
    public void recipe(boolean easy){
    	if(easy == true){
    		GameRegistry.addShapedRecipe(new ItemStack(this), new Object[]{
    			"XXX",
    			"XIX",
    			"XXX",
    			'X', frameMat,
    			'I', bindingMat});
    	}else{
    		RecipeManagers.carpenterManager.addRecipe(createTime, recFluid, null, new ItemStack(this), new Object[]{
    			"XXX",
    			"XIX",
    			"XXX",
    			'X', frameMat,
    			'I', bindingMat});
    	}
    }
    
	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return territorymodifier;
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate,
			float currentModifier) {
		return mutationmodifier;
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate,
			float currentModifier) {
		return lifespanmodifier;
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return productionmodifier;
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return flowering;
	}

	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return decay;
	}

	@Override
	public boolean isSealed() {
		return sealed;
	}

	@Override
	public boolean isSelfLighted() {
		return selflighted;
	}

	@Override
	public boolean isSunlightSimulated() {
		return simulated;
	}

	@Override
	public boolean isHellish() {
		return helish;
	}

	@Override
	public ItemStack frameUsed(IBeeHousing housing, ItemStack frame,
			IBee queen, int wear) {
		frame.setItemDamage(frame.getItemDamage() + 1);
		if(frame.getItemDamage() >= frame.getMaxDamage())
			return null;
		else
			return frame;
	}
	
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
			return color;
		}
		return new Color(255, 255, 255).getRGB();
	}
	
	@Override
    public void registerIcons(IIconRegister ir) {
		this.primary = ir.registerIcon(Main.modName.toLowerCase() + ":" + "frame_0");
		this.secondary = ir.registerIcon(Main.modName.toLowerCase() + ":" + "frame_1");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
			list.add(EnumChatFormatting.RED + "Durability: " + EnumChatFormatting.GRAY + uses);
			list.add(EnumChatFormatting.RED + "isHelish: " + EnumChatFormatting.GRAY + helish);
			list.add(EnumChatFormatting.RED + "isSimulated: " + EnumChatFormatting.GRAY + simulated);
			list.add(EnumChatFormatting.RED + "isSelflighted: " + EnumChatFormatting.GRAY +  selflighted);
			list.add(EnumChatFormatting.RED + "isSealed: " + EnumChatFormatting.GRAY + sealed);
			list.add(EnumChatFormatting.RED + "Decay: " + EnumChatFormatting.GRAY +  String.format("%.0f", decay * 100) + "%");
			list.add(EnumChatFormatting.RED + "Flowering: " + EnumChatFormatting.GRAY + String.format("%.0f", flowering * 100) + "%");
			list.add(EnumChatFormatting.RED + "Production Modifier: " + EnumChatFormatting.GRAY +  String.format("%.0f", productionmodifier * 100) + "%");
			list.add(EnumChatFormatting.RED + "Lifespan Modifier: " + EnumChatFormatting.GRAY +  String.format("%.0f", lifespanmodifier * 100) + "%");
			list.add(EnumChatFormatting.RED + "Mutation Modifier: " + EnumChatFormatting.GRAY + String.format("%.0f", mutationmodifier * 100) + "%");
			list.add(EnumChatFormatting.RED + "Territory Modifier: " + EnumChatFormatting.GRAY + String.format("%.0f", territorymodifier * 100) + "%");
		}else{
			list.add(EnumChatFormatting.GREEN + "Press " + "Shift " + "for more info!");
		}
	}
}