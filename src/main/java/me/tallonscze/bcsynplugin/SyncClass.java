package me.tallonscze.bcsynplugin;

import me.tallonscze.bcsynplugin.database.DatabaseManager;
import me.tallonscze.bcsynplugin.luckperms.LuckpermsProvider;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class SyncClass implements Listener {
    static DatabaseManager database = new DatabaseManager("127.0.0.1", 3306, "bc_skyblock", "afterlife", "Z8bF77NL9I5A");
    static LuckpermsProvider luckAPI = new LuckpermsProvider();
    @EventHandler
    public void playerJoinEvenet(PlayerJoinEvent event){
        Player player = event.getPlayer();
        syncPlayer(player);
    }

    public void syncPlayer(Player player){
        int refreshTime = 15 * 60;
        new BukkitRunnable(){
            @Override
            public void run(){
                if(!player.isOnline()){
                    this.cancel();
                }
                String group = luckAPI.getRank(player);
                database.setRankToDatabase(player, group);
            }

        }.runTaskTimer(Bcsynplugin.INSTANCE, 0, refreshTime*20);
    }


}
