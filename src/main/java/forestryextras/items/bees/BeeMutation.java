package forestryextras.items.bees;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.oredict.OreDictionary;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IGenome;
import forestryextras.main.init.FEBees;

public class BeeMutation implements IBeeMutation{	
	public BeeMutation(IAlleleBeeSpecies species0, IAlleleBeeSpecies species1, int percentChance, boolean requiresBlock, Species result, String mod){
		this.parent1 = species0;
		this.parent2 = species1;
		this.mutationTemplate = result.getGenome();
		this.baseChance = percentChance;
		this.isSecret = false;
		this.requiresBlock = false;
		this.requiredBlockOreDictEntry = null;
		this.requiredBlockName = null;
	
		this.getRoot().registerMutation(this);
	}
	private IAllele parent1;
	private IAllele parent2;
	private IAllele mutationTemplate[];
	private int baseChance;
	private boolean isSecret;
	private boolean requiresBlock;
	private Block requiredBlock;
	private String requiredBlockOreDictEntry;
	private String requiredBlockName;
	
	@Override
	public float getChance(IBeeHousing housing, IAlleleBeeSpecies allele0, IAlleleBeeSpecies allele1, IBeeGenome genome0, IBeeGenome genome1) {
		float chance = 1.0F;
		for (IBeeModifier beemod : housing.getBeeModifiers()) {
			beemod.getMutationModifier(genome0, genome1, chance);
		}
		int processedChance = Math.round(this.baseChance * chance * getRoot().getBeekeepingMode(housing.getWorld()).getBeeModifier().getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0F));

		if(processedChance <= 0.0F) {
			return 0.0F;
		}
		if((this.parent1.getUID().equals(allele0.getUID())) && (this.parent2.getUID().equals(allele1.getUID())))
			return processedChance;
		if((this.parent2.getUID().equals(allele0.getUID())) && (this.parent1.getUID().equals(allele1.getUID()))) {
			return processedChance;
		}
		return 0.0F;
	}

	@Override
	public IAllele[] getTemplate(){
		return mutationTemplate;
	}

	@Override
	public float getBaseChance(){
		return baseChance;
	}

	@Override
	public boolean isPartner(IAllele allele){
		return parent1.getUID().equals(allele.getUID()) || parent2.getUID().equals(allele.getUID());
	}

	@Override
	public IAllele getPartner(IAllele allele){
		IAllele val = parent1;
		if (val.getUID().equals(allele.getUID()))
			val = parent2;
		return val;
	}

	@Override
	public IBeeRoot getRoot(){
		return FEBees.beeRoot;
	}
	
	public boolean arePartners(IAllele alleleA, IAllele alleleB){
		return (this.parent1.getUID().equals(alleleA.getUID())) && this.parent2.getUID().equals(alleleB.getUID()) ||
				this.parent1.getUID().equals(alleleB.getUID()) && this.parent2.getUID().equals(alleleA.getUID());
	}
	
	public BeeMutation setSecret(){
		this.isSecret = true;
		
		return this;
	}

	public boolean isSecret(){
		return isSecret;
	}
	
	public BeeMutation setBlockRequired(Block block){
		this.requiresBlock = true;
		this.requiredBlock = block;
		
		return this;
	}

	
	public BeeMutation setBlockAndMetaRequired(Block block, int meta){
		this.requiresBlock = true;
		this.requiredBlock = block;
		
		return this;
	}
	
	public BeeMutation setBlockRequired(String oreDictEntry){
		this.requiresBlock = true;
		this.requiredBlockOreDictEntry = oreDictEntry;
		
		return this;
	}
	
	public BeeMutation setBlockRequiredNameOverride(String blockName){
		this.requiredBlockName = blockName;
		
		return this;
	}
	
	public BeeMutation setBiomeRequired(BiomeDictionary.Type biomeType){
		return this;
	}

	@Override
	public Collection<String> getSpecialConditions() {
		ArrayList<String> conditions = new ArrayList<String>();
		
		if (this.requiresBlock){
			if (this.requiredBlockName != null){
				conditions.add(String.format("Requires Block ", requiredBlockName));
			}else if (this.requiredBlockOreDictEntry != null){
				ArrayList<ItemStack> ores = OreDictionary.getOres(this.requiredBlockOreDictEntry);
				if (ores.size() > 0){
					conditions.add(String.format("Requires Block ", ores.get(0).getDisplayName()));
				}
			}else if (this.requiredBlock != null) {
				conditions.add(String.format("Requires Block ", requiredBlock.getLocalizedName()));
			}
		}
		return conditions;
	}

	@Override
	public IAlleleSpecies getAllele0() {
		return (IAlleleSpecies) this.parent1;
	}

	@Override
	public IAlleleSpecies getAllele1() {
		return (IAlleleSpecies) this.parent2;
	}
}