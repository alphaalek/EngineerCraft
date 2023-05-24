package me.alek.utils;

import org.bukkit.util.Vector;

public class FacingUtils {

    public static boolean isBetween(double lower, double test, double upper) {
        if (lower > test) {
            return false;
        }
        if (upper <= test) {
            return false;
        }
        return true;
    }

    public static Vector getFacingVector(int yaw) {
        if (yaw < 0) {
            yaw = 360 + yaw;
        }
        if (isBetween(0, yaw, 45) || isBetween(315, yaw, 360)) {
            return new Vector(0, 0, 1);
        }
        if (isBetween(45, yaw, 135)) {
            return new Vector(-1, 0, 0);
        }
        if (isBetween(135, yaw, 225)) {
            return new Vector(0, 0, -1);
        }
        if (isBetween(225, yaw, 315)) {
            return new Vector(1, 0, 0);
        }
        return new Vector(0, 0, 1);
    }
}
