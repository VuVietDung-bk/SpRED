package objects.infectious.vaccines;

import objects.infectious.Vaccine;

public class VaccineFactory {
	public static Vaccine createVaccine(int id, int x, int y) {
        switch (id) {
            case 1:
                //return new CrossVirus(x, y);
            case 2:
                //return new XVirus(x, y);
            // Add more cases for other virus types
            default:
                throw new IllegalArgumentException("Unknown virus ID: " + id);
        }
    }
}
