package me.niklas.aoc.twentynineteen.days.orbits;

import java.util.List;

/**
 * Created by Niklas on 06.12.2019 in twentynineteen
 */
public class Planet {

    public static final Planet NONE = new Planet("NONE");
    private final String code;
    private Planet orbiting = null;

    public Planet(String code) {
        this.code = code;
    }

    public void setOrbitingPlanet(Planet p) {
        if (p.equals(this)) {
            System.out.println("Planet can not orbit itself");
            System.exit(-1);
        }
        orbiting = p;
    }

    public String getCode() {
        return code;
    }

    public Planet getOrbiting() {
        return orbiting != null ? orbiting : Planet.NONE;
    }

    public boolean isCode(String code) {
        return this.code.equals(code);
    }

    public int indirectOrbits() {
        return orbiting != null ? 1 + orbiting.indirectOrbits() : 0;
    }

    public int distanceTo(Planet b) {
        return orbiting != null && !orbiting.equals(b) ? 1 + orbiting.distanceTo(b) : 0;
    }

    public void fillWithOrbits(List<Planet> planets) {
        planets.add(this);
        if (orbiting != null) orbiting.fillWithOrbits(planets);
    }

    public boolean isOrbiting(String code) {
        if (this.code.equals(code)) return true;
        else if (orbiting != null) return orbiting.isOrbiting(code);
        else return false;
    }
}
