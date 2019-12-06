package me.niklas.aoc.twentynineteen.days.orbits;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Niklas on 06.12.2019 in twentynineteen
 */
public class OrbitalMap {

    private final List<Planet> planets = new ArrayList<>();

    public OrbitalMap(List<String> inputs) {
        inputs.forEach(line -> {
            Planet a = get(line.split("\\)")[0], true);
            Planet b = get(line.split("\\)")[1], true);
            b.setOrbitingPlanet(a);
        });
    }

    private Planet get(String code, boolean createNew) {
        Optional<Planet> p = planets.stream().filter(i -> i.isCode(code)).findFirst();
        if (p.isPresent()) {
            return p.get();
        } else if (createNew) {
            Planet newPlanet = new Planet(code);
            planets.add(newPlanet);
            return newPlanet;
        } else return Planet.NONE;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public int getOrbitCount() {
        AtomicInteger i = new AtomicInteger();
        planets.forEach(p -> i.getAndAdd(p.indirectOrbits()));
        return i.get();
    }

    public int getOrbitCountOf(String code) {
        Planet p = get(code, false);
        if (p != null) return p.indirectOrbits();
        else return -1;
    }

    public int distanceBetween(String first, String second) {

        Planet a = get(first, true);
        Planet b = get(second, true);

        if (a.isOrbiting(second)) {
            System.out.println("Direct return for " + first + " and " + second);
            return a.distanceTo(b);
        } else {
            List<Planet> firstList = new ArrayList<>();
            List<Planet> secondList = new ArrayList<>();
            a.fillWithOrbits(firstList);

            b.fillWithOrbits(secondList);

            Optional<Planet> firstInt = firstList.stream().filter(secondList::contains).findFirst();
            if (firstInt.isEmpty()) {
                System.err.println("No intersection between " + first + " and " + second);
                return -1;
            } else {
                return a.distanceTo(firstInt.get()) + b.distanceTo(firstInt.get());
            }
        }
    }
}
