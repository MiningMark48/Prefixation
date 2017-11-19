package com.miningmark48.prefixmod.commands;

import com.miningmark48.prefixmod.reference.prefixes.PrefixTypes;
import com.miningmark48.prefixmod.reference.prefixes.ToolPrefixes;
import com.miningmark48.prefixmod.reference.prefixes.WeaponPrefixes;
import com.miningmark48.prefixmod.utility.HandlePrefix;
import jdk.internal.org.objectweb.asm.Handle;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandReforge extends CommandBase {

    private final String prefix = "prefix";

    public CommandReforge(){

    }

    @Override
    public String getName() {
        return String.format("%s_reforge", prefix);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return String.format("%s_reforge", prefix);
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add(String.format("%s_reforge", prefix));
        return aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
            if (!heldItem.isEmpty() && args.length >= 1) {
                if (args[0].equalsIgnoreCase("weapon")) {
                    HandlePrefix.reforgePrefix(heldItem, PrefixTypes.WEAPON, WeaponPrefixes.prefixNameMap, WeaponPrefixes.modifierMap, WeaponPrefixes.modifierNameMap);
                    sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Reforged!"));
                } else if (args[0].equalsIgnoreCase("tool")) {
                    HandlePrefix.reforgePrefix(heldItem, PrefixTypes.TOOL, ToolPrefixes.prefixNameMap, ToolPrefixes.modifierMap, ToolPrefixes.modifierNameMap);
                    sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Reforged!"));
                } else {
                    player.sendMessage(new TextComponentString(TextFormatting.RED + "Error: Invalid args"));
                }
            } else {
                player.sendMessage(new TextComponentString(TextFormatting.RED + "Error: Empty hand or missing args"));
            }
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        List<String> autocompletes = new ArrayList<>();
        autocompletes.add("weapon");
        autocompletes.add("tool");
        return autocompletes;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

}