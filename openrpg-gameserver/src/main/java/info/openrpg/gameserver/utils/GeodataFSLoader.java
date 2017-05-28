package info.openrpg.gameserver.utils;

import info.openrpg.gameserver.model.world.Chunk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class GeodataFSLoader implements IGeodata {
    public static final String GEODATAFILE = "src/main/resources/geodata/sample.bin";
    public final int CHUNK_SIZE = 9;
    private final List<byte[]> geodata;

    public GeodataFSLoader() throws IOException {
        this.geodata = readGeoFromFile(GEODATAFILE);
    }

    @Override
    public Chunk[][] loadFullMap() {

        return new Chunk[0][];
    }

    @Override
    public int getWorldXSize() {

        return geodata.size() / CHUNK_SIZE;
    }

    @Override
    public int getWorldYSize() {
        return geodata.get(0).length / CHUNK_SIZE;
    }

    private List<byte[]> readGeoFromFile(String geoFileName) throws IOException {
        Path path = Paths.get(geoFileName);
        List<byte[]> geoList = Files.lines(path).map((x) -> x.getBytes()).collect(Collectors.toList());
        return geoList;
    }
}
