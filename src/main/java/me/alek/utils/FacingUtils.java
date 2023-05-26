package me.alek.utils;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class FacingUtils {

    private enum Facing {
        SOUTH((i) -> isBetween(0, i, 45) || isBetween(315, i, 360), BlockFace.SOUTH, new Vector(0, 0, 1)),
        WEST((i) -> isBetween(45, i, 135), BlockFace.WEST, new Vector(-1, 0, 0)),
        NORTH((i) -> isBetween(135, i, 225), BlockFace.NORTH, new Vector(0, 0, -1)),
        EAST((i) -> isBetween(225, i, 315), BlockFace.EAST, new Vector(1, 0, 0));

        private final Predicate<Double> predicate;
        private final BlockFace asBlockFace;
        private final Vector asVector;

        Facing(Predicate<Double> predicate, BlockFace asBlockFace, Vector asVector) {
            this.predicate = predicate;
            this.asBlockFace = asBlockFace;
            this.asVector = asVector;
        }
    }

    private enum BinaryBlockFaces {
        SOUTH(BlockFace.SOUTH, 1),
        WEST(BlockFace.WEST,2 ),
        NORTH(BlockFace.NORTH, 3),
        EAST(BlockFace.EAST, 4);

        private final int id;
        private final BlockFace blockFace;

        BinaryBlockFaces(BlockFace blockFace, int id) {
            this.id = id;
            this.blockFace = blockFace;
        }
    }

    static private final HashMap<BlockFace, Integer> binaryBlockfaceMap = new HashMap<>();
    static private final HashMap<Integer, BlockFace> reversedBinaryBlockfaceMap = new HashMap<>();

    static {
        for (BinaryBlockFaces binaryBlockface : BinaryBlockFaces.values()) {
            binaryBlockfaceMap.put(binaryBlockface.blockFace, binaryBlockface.id);
            reversedBinaryBlockfaceMap.put(binaryBlockface.id, binaryBlockface.blockFace);
        }
    }

    public static int relativeDirectionDistance(BlockFace relative, BlockFace facing) {
        int relativeId = binaryBlockfaceMap.get(relative);
        int facingId = binaryBlockfaceMap.get(facing);

        if (relativeId == facingId) {
            return 0;
        }
        if (relativeId < facingId) {
            return facingId - relativeId;
        }
        else {
            return 4 - relativeId + facingId;
        }
    }

    public static BlockFace getRelativeDirection(int distance, BlockFace facing) {
        if (distance == 0) {
            return facing;
        }
        else if (distance + binaryBlockfaceMap.get(facing) <= 4) {
            return reversedBinaryBlockfaceMap.get(distance + binaryBlockfaceMap.get(facing));
        }
        else {
            return reversedBinaryBlockfaceMap.get(distance + binaryBlockfaceMap.get(facing) - 4);
        }
    }

    public static Function<BlockFace, BlockFace> getRotateBlockFaceFunction(BlockFace relative, BlockFace facing) {
        final int distance = relativeDirectionDistance(relative, facing);
        return (blockFace) -> getRelativeDirection(distance, blockFace);
    }

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
        for (Facing facing : Facing.values()) {
            if (facing.predicate.test((double) yaw)) {
                return facing.asVector;
            }
        }
        return new Vector(0, 0, 1);
    }

    public static BlockFace getFacingBlockFace(int yaw) {
        if (yaw < 0) {
            yaw = 360 + yaw;
        }
        for (Facing facing : Facing.values()) {
            if (facing.predicate.test((double) yaw)) {
                return facing.asBlockFace;
            }
        }
        return BlockFace.NORTH;
    }

    public static Function<Vector, Vector> getRotateVectorFunction(BlockFace direction) {
        if (direction == BlockFace.SOUTH) {
            return (vector) -> vector;
        }
        else if (direction == BlockFace.WEST) {
            return (vector) -> new Vector(vector.getBlockZ() * (-1), 0, vector.getBlockX());
        }
        else if (direction == BlockFace.NORTH) {
            return (vector) -> vector.clone().multiply(-1);
        }
        else {
            return (vector) -> new Vector(vector.getBlockZ(), 0, vector.getBlockX() * (-1));
        }
    }

    public static Location reverseVectorForLocation(Location location, Vector vector, Function<Vector, Vector> vectorRotation) {
        return location.getWorld().getBlockAt(location.clone().add(reverseVectorForVector(vector, vectorRotation))).getLocation();
    }

    public static Vector reverseVectorForVector(Vector vector, Function<Vector, Vector> vectorRotation) {
        return vectorRotation.apply(vector);
    }

    public static byte getLeverBlockFaceData(BlockFace blockFace) {
        switch (blockFace) {
            case EAST:
                return 1;
            case WEST:
                return 2;
            case SOUTH:
                return 3;
            default:
                return 4;
        }
    }

    public static byte getSignBlockFaceData(BlockFace blockFace) {
        switch (blockFace) {
            case SOUTH:
                return 2;
            case NORTH:
                return 3;
            case EAST:
                return 4;
            default:
                return 5;
        }
    }
}
