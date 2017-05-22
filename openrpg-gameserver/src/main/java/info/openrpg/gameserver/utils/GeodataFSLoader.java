package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeodataFSLoader implements IGeodata {
    public static final String GEODATAFILE = "src/main/resources/geodata/sample.bin";
    private final byte[] geodata;

    public GeodataFSLoader() {
        this.geodata = readGeaFromFile(GEODATAFILE);
    }

    @Override
    public Chunk[][] loadFullMap() {

        return new Chunk[0][];
    }

    @Override
    public int getWorldSize() {

        return geodata.length;
    }

    private byte[] readGeaFromFile(String geoFileName) {
        Path path = Paths.get(geoFileName);
        byte[] result = null;
        try {
            result = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
