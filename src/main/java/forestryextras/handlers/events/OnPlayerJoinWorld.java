package forestryextras.handlers.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import wasliecore.helpers.UpdateHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestryextras.main.Main;

public class OnPlayerJoinWorld {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
    public void joinEvent(EntityJoinWorldEvent e){
		if(!e.world.isRemote){
			if(e.entity instanceof EntityPlayer){
				EntityPlayer player = (EntityPlayer)e.entity;
				if(player.getDisplayName() != null){
					if(UpdateHelper.getUpdate(Main.alias + "2") != null){
						if(UpdateHelper.getVersion(Main.alias + "2") != null && UpdateHelper.getVersion(Main.alias + "2") > Main.version){
							if(UpdateHelper.getText(Main.alias + "2") != null){
								sendMessage("There is a new version of Forestry Extras 2 available", player, EnumChatFormatting.RED);
								sendMessage(UpdateHelper.getText(Main.alias + "2"), player, EnumChatFormatting.GRAY);
							}
						}
					}
				}
			}
		}
    }
	
	public void sendMessage(String text, EntityPlayer player, EnumChatFormatting color){
		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "[" + Main.alias + "2" +  "] " + color + text));
	}
}