package me.alek.mechanics.structures;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class MechanicStructure extends Structure implements IMechanicStructure {

    private Sign sign = new Sign(new Vector(0, 0, 0), BlockFace.NORTH);

    public void setSign(int x, int y, int z, BlockFace blockFace) {
        sign = new Sign(new Vector(x, y, z), blockFace);
    }

    @Override
    public Sign getSign() {
        return sign;
    }

    @Override
    public void combineStructure(IStructure structure) {
        if (structure instanceof IMechanicStructure) {
            final IMechanicStructure mechanicStructure = (IMechanicStructure) structure;
            final Vector vector = mechanicStructure.getSign().getVector();
            if (vector.getX() != 0 && vector.getY() != 0 && vector.getZ() != 0) {
                return;
            }
            this.sign = mechanicStructure.getSign();
        }
        super.combineStructure(structure);
    }

    private boolean compareVector(Vector vector1, Vector vector2) {
        return vector1.getBlockX() == vector2.getBlockX() && vector1.getBlockZ() == vector2.getBlockZ();
    }

    @Override
    public void parse() {
        final Vector vector = sign.getVector();
        Vector sameVector = null;

        for (Vector mapVector : getPillars().keySet()) {
            if (compareVector(vector, mapVector)) {
                sameVector = mapVector;
                break;
            }
        }
        final Pillar signPillar;
        if (sameVector != null) {
            signPillar = getPillars().get(sameVector).clearYOffset().addCallback(vector.getBlockY(), Material.WALL_SIGN, sign.getBlockFace(), org.bukkit.material.Sign::new);
        } else {
            signPillar = new Pillar().addCallback(vector.getBlockY(), Material.WALL_SIGN, sign.getBlockFace(), org.bukkit.material.Sign::new);
        }
        set(vector.getBlockX(), vector.getBlockZ(), signPillar);
    }
}