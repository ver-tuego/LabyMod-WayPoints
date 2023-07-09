package com.example.addon;

import java.util.ArrayList;
import java.util.List;

public class WayPointRenderManager {

    private static List<List<Double>> coordinates;

    public WayPointRenderManager() {
            coordinates = new ArrayList<>();
    }

    public static void addWayPoint(double x, double y, double z) {
        List<Double> coordinate = new ArrayList<>();
        coordinate.add(x);
        coordinate.add(y);
        coordinate.add(z);
        coordinates.add(coordinate);
    }

    public static List<List<Double>> getWaypoints() {
        return coordinates;
    }

}
