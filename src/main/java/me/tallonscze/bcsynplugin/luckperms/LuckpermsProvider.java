package me.tallonscze.bcsynplugin.luckperms;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.entity.Player;

public class LuckpermsProvider {
    public static User getUser(Player player){
        return LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId());
    }

    public String getRank(Player player){
        User user = getUser(player);
        return user.getPrimaryGroup();
    }

    public void setRank(Player player, String rank){
        User user = getUser(player);
        user.data().add(Node.builder("group."+rank).build());
        LuckPermsProvider.get().getUserManager().saveUser(user);
    }
}
