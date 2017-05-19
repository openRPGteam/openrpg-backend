package info.openrpg.image.processing;

import info.openrpg.image.processing.dto.ChunkDTO;

import java.io.InputStream;
import java.util.Optional;

public interface RequestSender {
    String ping();
    Optional<InputStream> sendMove(long id, int x, int y);
    Optional<InputStream> spawnPlayer(long id);
    Optional<InputStream> createImage(ChunkDTO chunkDTO);
}
