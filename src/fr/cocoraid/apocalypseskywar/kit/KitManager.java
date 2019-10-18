package fr.cocoraid.apocalypseskywar.kit;

import fr.cocoraid.apocalypseskywar.kit.kits.ClassicKit;
import fr.cocoraid.apocalypseskywar.kit.kits.FinalDestinationKit;

import java.util.HashMap;
import java.util.Map;

public class KitManager {

    private Map<String, Kit> kits = new HashMap<>();

    public KitManager() {
        registerKits();
    }

    private void registerKits() {
        kits.put("classic",new ClassicKit());
        kits.put("finaldestination",new FinalDestinationKit());

    }


    public Map<String, Kit> getKits() {
        return kits;
    }
}
