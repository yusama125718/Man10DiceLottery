package yusama125718_209282ihcuobust.man10dicelottery;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static java.lang.Integer.parseInt;

public final class Man10Dicelottery extends JavaPlugin
{
    static public List<UUID> dissableplayers=new ArrayList<>();
    static boolean operation = false;
    static boolean activegame = false;
    static int dicestakes;
    static int luckynumber;
    HashMap<Integer,UUID> appliedplayers = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            switch (args.length)
            {
                case 1:
                {
                    Player playerid = (Player) sender;

                    if (args[0].equals("hide"))
                    {
                        if (dissableplayers.contains(playerid.getUniqueId()))
                        {
                            return true;
                        }
                        else
                        {
                            dissableplayers.add(playerid.getUniqueId());
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§7§l非表示にします");

                            return true;
                        }
                    }

                    if (args[0].equals("show"))
                    {
                        if (dissableplayers.contains(playerid.getUniqueId()))
                        {
                            dissableplayers.remove(playerid.getUniqueId());
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§7§l表示します");

                            return true;
                        } else
                        {
                            return true;
                        }
                    }


                    if (args[0].length() <= 10)
                    {
                        boolean isNumeric = args[0].matches("-?\\d+");
                        if (isNumeric)
                        {
                            if (activegame)
                            {
                                luckynumber = parseInt(args[0]);

                                if (dicestakes < luckynumber)
                                {
                                    sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l" + dicestakes + "以下の整数にしてください");
                                    return true;
                                }
                                if (1 > luckynumber)
                                {
                                    sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l1以上の整数にしてください");
                                    return true;
                                }

                                if (!appliedplayers.values().contains(playerid.getUniqueId()))
                                {
                                    if (!appliedplayers.keySet().contains(luckynumber))
                                    {
                                        appliedplayers.put(luckynumber,playerid.getUniqueId());
                                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§e§l応募しました！");
                                        return true;
                                    }
                                    else
                                    {
                                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§lその数字はすでに使われています");
                                        return true;
                                    }
                                }
                                else
                                {
                                    sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§lあなたはすでに応募しています");
                                    return true;
                                }
                            }
                            else
                            {
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l現在応募は受け付けていません");
                                return true;
                            }
                        }
                        else
                        {
                            if (sender.hasPermission("mdicelottery.op"))
                            {
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [ダイスの面数] [時間(秒)] : §lゲームを開始します");
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery hede : §l非表示にします");
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery show : §l表示します");
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [数字] : §l抽選に応募します(ゲーム中のみ)");
                                return true;
                            }
                            else
                            {
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery hede : §l非表示にします");
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery show : §l表示します");
                                sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [数字] : §l抽選に応募します(ゲーム中のみ)");
                                return true;
                            }
                        }
                    }
                    else
                    {
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l応募する数字は10億以上に設定できません");
                        return true;
                    }
                }

                case 2:
                {
                    if (sender.hasPermission("mdicelittery.op"))
                    {
                        if (args[0].length() >= 10)
                        {
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§lダイスの面数は10億以上に設定できません");
                            return true;
                        }

                        if (args[1].length() >= 3)
                        {
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l時間は100秒以上に設定できません");
                            return true;
                        }

                        if (!operation)
                        {
                            boolean isNumeric = args[0].matches("-?\\d+");
                            if (isNumeric)
                            {
                                boolean isNumeric1 = args[1].matches("-?\\d+");
                                if (isNumeric1)
                                {
                                    dicestakes = parseInt(args[0]);
                                    int dicedelay = parseInt(args[1]);

                                    operation = true;
                                    activegame = true;
                                    for (Player player : Bukkit.getOnlinePlayers())
                                    {
                                        if (!dissableplayers.contains(player.getUniqueId()))
                                        {
                                            player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r" + sender.getName() + "§lが" + args[0] + "§lDを開始しました！ §e/mdicelottery [数字] §r§lで参加しましょう！");
                                        }
                                    }
                                    int outnumber;

                                    Random dicerondom = new Random();
                                    outnumber = dicerondom.nextInt(dicestakes) + 1;

                                    Bukkit.getScheduler().runTaskLater(this, new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            activegame = false;

                                            for (Player player : Bukkit.getOnlinePlayers())
                                            {
                                                if (!dissableplayers.contains(player.getUniqueId()))
                                                {
                                                    player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§lダイスを振っています...");
                                                }
                                            }
                                        }
                                    }, dicedelay * 20L);

                                    Bukkit.getScheduler().runTaskLater(this, new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            {
                                                for (Player player : Bukkit.getOnlinePlayers())
                                                {
                                                    if (!dissableplayers.contains(player.getUniqueId()))
                                                    {
                                                        player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r" + sender.getName() + "§lは" + dicestakes + "面ダイスを振って §e§l" + outnumber + "§r§l を出しました！");
                                                    }
                                                }

                                                if (appliedplayers.keySet().contains(outnumber))
                                                {
                                                    for (Player player : Bukkit.getOnlinePlayers())
                                                    {
                                                        if (!dissableplayers.contains(player.getUniqueId()))
                                                        {
                                                            player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r" + Bukkit.getOfflinePlayer(appliedplayers.get(outnumber)).getName() + "§lは" + outnumber + "§lをピッタリ予想しました！");
                                                        }
                                                    }
                                                }
                                                if (appliedplayers.keySet().contains(outnumber + 1))
                                                {
                                                    for (Player player : Bukkit.getOnlinePlayers())
                                                    {
                                                        if (!dissableplayers.contains(player.getUniqueId()))
                                                        {
                                                            player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r" + Bukkit.getOfflinePlayer(appliedplayers.get(outnumber + 1)).getName() + "§lは" + outnumber + "§lを1つ多く予想しました！");
                                                        }
                                                    }
                                                }
                                                if (appliedplayers.keySet().contains(outnumber - 1))
                                                {
                                                    for (Player player : Bukkit.getOnlinePlayers())
                                                    {
                                                        if (!dissableplayers.contains(player.getUniqueId()))
                                                        {
                                                            player.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r" + Bukkit.getOfflinePlayer(appliedplayers.get(outnumber - 1)).getName() + "§lは" + outnumber + "§lを 1つ少なく 予想しました！");
                                                        }
                                                    }
                                                }
                                                appliedplayers.clear();
                                                operation = false;
                                            }
                                        }
                                    }, dicedelay * 20L + 60L);
                                    return true;
                                }
                                else
                                {
                                    sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§l時間は1以上の整数にしてください");
                                    return true;
                                }
                            }
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§lダイスの面数は1以上の整数にしてください");
                            return true;
                        }
                        else
                        {
                            sender.sendMessage("§l[§e§lMan10DiceLottery§f§l]§r§c§lゲームが進行中です");
                        }
                    }
                    else
                    {
                        sender.sendMessage("§c[Man10DiceLottery]You don't have permission!");
                        return true;
                    }
                }

                default:
                {
                    if (sender.hasPermission("mdicelottery.op"))
                    {
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [ダイスの面数] [時間(秒)] : §lゲームを開始します");
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery hede : §l非表示にします");
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery show : §l表示します");
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [数字] : §l抽選に応募します(ゲーム中のみ)");
                        return true;
                    }
                    else
                    {
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery hede : §l非表示にします");
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery show : §l表示します");
                        sender.sendMessage("§l[§e§lMan10DiceLottery§f§l] §7/mdicelottery [数字] : §l抽選に応募します(ゲーム中のみ)");
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
