package objects.infectious.viruses;

import objects.infectious.Virus;

public class VirusFactory {
    public static Virus createVirus(int id, int x, int y) {
        switch (id) {
            case 1:
                return new CrossVirus(x, y);
            case 2:
                return new XVirus(x, y);
            case 3:
                return new YVirus(x, y);
            default:
                throw new IllegalArgumentException("Unknown virus ID: " + id);
        }
    }
}
