package info.openrpg.ejb.utils;

import info.openrpg.ejb.enums.TerrainType;
import info.openrpg.ejb.model.world.Chunk;

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

    public GeodataFSLoader(String geoFile) throws IOException {
        this.geodata = readGeoFromFile(geoFile);
    }

    @Override
    public Chunk[][] loadFullMap() {
        Chunk[][] result = new Chunk[getWorldXSize()][getWorldYSize()];
        for (int i = 0; i < getWorldXSize(); i++) {
            for (int j = 0; j < getWorldYSize(); j++) {
                result[i][j] = getChunk(i, j);
            }
        }
        return result;
    }

    @Override
    public int getWorldXSize() {
        return geodata.size() / CHUNK_SIZE;
    }

    @Override
    public int getWorldYSize() {
        return geodata.get(0).length / CHUNK_SIZE;
    }

    @Override
    public int getWorldXSizeCells() {
        return geodata.size();
    }

    @Override
    public int getWorldYSizeCells() {
        return geodata.get(0).length;
    }

    @Override
    public Chunk getChunk(int x, int y) {
        if (x > getWorldXSize() || y > getWorldYSize())
            return new Chunk();
        TerrainType[][] result = new TerrainType[CHUNK_SIZE][CHUNK_SIZE];
        for (int i = 0; i < CHUNK_SIZE; i++) {
            for (int j = 0; j < CHUNK_SIZE; j++) {
                TerrainType cell = TerrainType.valueOf(geodata.get(x * CHUNK_SIZE + i)[y * CHUNK_SIZE + j] - 48);
                // System.out.println(cell);
                result[i][j] = cell;
            }
        }
        return new Chunk(result);
    }

    private List<byte[]> readGeoFromFile(String geoFileName) throws IOException {
        Path path = Paths.get(geoFileName);
        List<byte[]> geoList = Files.lines(path).map((x) -> x.getBytes()).collect(Collectors.toList());
        return geoList;
    }
}
