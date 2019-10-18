package fr.cocoraid.apocalypseskywar.command;

import fr.cocoraid.apocalypseskywar.ApocalypseSkywar;
import fr.cocoraid.apocalypseskywar.arena.Cuboid;
import fr.cocoraid.apocalypseskywar.map.MapInfo;
import fr.cocoraid.apocalypseskywar.map.MapManager;
import fr.cocoraid.apocalypseskywar.map.MapSerializer;
import fr.cocoraid.apocalypseskywar.position.Point;
import fr.cocoraid.apocalypseskywar.position.WorldMap;
import fr.cocoraid.apocalypseskywar.utils.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PositionsCMD implements CommandExecutor {


    private MapInfo tempWorldMap;
    private Point pointA;
    private Point pointB;
    private MapSerializer serializer;
    private File saveDir;
    private MapManager mapManager;
    public PositionsCMD(ApocalypseSkywar instance) {
        this.saveDir = new File(instance.getDataFolder(),"/maps/");
        this.serializer = instance.getMapSerializer();
        this.mapManager = instance.getMapManager();
    }

    private void displayHelp(Player p) {
        p.sendMessage("§7Help commands:" +
                "\n/sk setup" +
                "\n/sk cancel" +
                "\n/sk setcuboid <a/b>" +
                "\n/sk setspawn" +
                "\n/sk addspawnpoint" +
                "\n/sk mapname <name>" +
                "\n/sk finish" +
                "\n/sk checkmap"
        );
    }

    private void checkTempworldCompleted(Player p) {
        if(tempWorldMap != null) {
            if(tempWorldMap.getPlayerSpawnPoints().size() < 2) {
                p.sendMessage("§cVous ne pouvez pas terminer l'initialisation, car il n'y a pas assez de spawn point pour les joueurs: /sk addspawnpoint");
                return;
            }
            if(tempWorldMap.getSpawn() == null) {
                p.sendMessage("§cVous devez initialiser un spawn de la map: /sk setspawn");
                return;
            }
            if(tempWorldMap.getMap() == null) {
                p.sendMessage("§cVous devez initialiser un nom de map: /sk mapname <name>");
            }
            if(pointA == null
                    || pointB == null) {
                p.sendMessage("§cVous devez initialiser un cuboid: /sk cuboid a, /sk cuboid b");
                return;
            }
            tempWorldMap.setCuboid(new Cuboid(pointA,pointB));
            //serialize
            final File file = new File(saveDir,"mapinfo.yml");
            final String json = serializer.serialize(tempWorldMap);
            FileUtils.save(file,json);

            tempWorldMap = null;
            pointA = null;
            pointB = null;
            p.sendMessage("§bL'initialisation du monde a été complété !");
            p.sendMessage("§bUn fichier a été généré dans le dossier du plugin");
            p.sendMessage("§bVous pouvez utiliser ce fichier dans le dossier monde");
        }
    }

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 0)
                displayHelp(p);
            if(args.length == 1) {
                String param = args[0];

                if(param.equalsIgnoreCase("setup")) {
                    if(tempWorldMap != null) {
                        p.sendMessage("§cUne template du world est déjà en cours de création" +
                                "\n§cPour l'annuler, veuillez utiliser /sk cancel");
                        return false;
                    }
                    tempWorldMap = new MapInfo();
                    p.sendMessage("§7Pour initialiser un nouveau monde veuillez utiliser les commandes suivantes:" +
                            "\n/sk mapname <mapName>" +
                            "\n/sk setcuboid <a/b>" +
                            "\n/sk setspawn" +
                            "\n/sk addspawnpoint" +
                            "\n/sk finish"
                    );
                }  else if(param.equalsIgnoreCase("finish")) {
                    if(tempWorldMap == null) {
                        p.sendMessage("§bVous devez d'abord utiliser la commande /sk setup");
                        return false;
                    }

                    checkTempworldCompleted(p);
                }
                else if(param.equalsIgnoreCase("cancel")) {
                    if(tempWorldMap != null) {
                        tempWorldMap = null;
                        pointA = null;
                        pointB = null;
                        p.sendMessage("§bL'initialisation du monde a été annulé");
                    } else p.sendMessage("§bAucunne initialisation à annuler...");
                } else if(param.equalsIgnoreCase("checkmap")) {
                    if(mapManager.getMapInfo() != null) {
                        MapInfo mapInfo = mapManager.getMapInfo();
                        p.sendMessage("Mapinfo: " +
                                        "\n§bmapname" + ChatColor.RESET + mapInfo.getMap().name() +
                                        "\n§bspawn: " + ChatColor.RESET + mapInfo.getSpawn() +
                                        "\n§bplayer spawn points size: " + ChatColor.RESET + mapInfo.getPlayerSpawnPoints().size() +
                                        "\n§bcuboid world " + ChatColor.RESET + mapInfo.getCuboid().getWorldName()

                                );
                    }
                } else {

                    Location l = p.getLocation();
                    if(param.equalsIgnoreCase("setspawn")) {
                        if(tempWorldMap == null) {
                            p.sendMessage("§bVous devez d'abord utiliser la commande /sk setup");
                            return false;
                        }

                        tempWorldMap.setSpawnPoint(new Point(l.getX(),l.getY(),l.getZ(),l.getYaw()));
                        p.sendMessage("§bLe hub est maintenant initialisé !");
                    } else if(param.equalsIgnoreCase("addspawnpoint")) {
                        if(tempWorldMap == null) {
                            p.sendMessage("§bVous devez d'abord utiliser la commande /sk setup");
                            return false;
                        }
                        tempWorldMap.getPlayerSpawnPoints().add(new Point(l.getX(),l.getY(),l.getZ(),l.getYaw()));
                        p.sendMessage("Vous avez initialisé le point de spawn numéro: " + (tempWorldMap.getPlayerSpawnPoints().size() - 1));
                    }


                }


            } else if(args.length == 2) {
                if(tempWorldMap == null) {
                    p.sendMessage("§bVous devez d'abord utiliser la commande /sk setup");
                    return false;
                }

                String param = args[0];
                if(param.equalsIgnoreCase("mapname")) {
                    String name = args[1];
                    WorldMap map = null;
                    for (WorldMap value : WorldMap.values()) {
                        if(value.name().equalsIgnoreCase(name)) {
                            map = value;
                            break;
                        }
                    }
                    if(map == null) {
                        p.sendMessage("§cLe nom de map que vous avez entré n'a pas été reconnu" +
                                "\n§cVoici une liste de noms: ");
                        for (WorldMap value : WorldMap.values()) {
                            p.sendMessage(" §6- " + value.name());
                        }
                        return false;
                    }
                    tempWorldMap.setMap(map);
                    p.sendMessage("§bLe nom de map " + map.name() + " est maintenant ajouté");

                } else if(param.equalsIgnoreCase("setcuboid")) {
                    String ab = args[1];
                    if(!ab.equalsIgnoreCase("a")
                            && !ab.equalsIgnoreCase("b")) {
                        p.sendMessage("§cCommande incorrecte: /sk setcuboid a et /sk setcuboid b");
                        return false;

                    }
                    Location l = p.getLocation();
                    if(ab.equalsIgnoreCase("a")) {
                        pointA = new Point(l.getX(),l.getY(),l.getZ(),l.getYaw());
                    } else if(ab.equalsIgnoreCase("b")) {
                        pointB = new Point(l.getX(),l.getY(),l.getZ(),l.getYaw());
                    }
                    p.sendMessage("§bLe point " + ab.toUpperCase() +  " du cuboid a été initialisé");
                }

            }

        }
        return false;
    }
}
