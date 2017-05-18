package info.openrpg.image.processing;

import java.io.InputStream;
import java.util.Optional;

public interface RequestSender {
    String ping();
    Optional<InputStream> sendMove(long id, int x, int y);
    Optional<InputStream> spawnPlayer(long id);
}
