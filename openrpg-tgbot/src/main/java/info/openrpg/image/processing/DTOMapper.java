package info.openrpg.image.processing;

import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.image.processing.dto.CellDTO;
import info.openrpg.image.processing.dto.ChunkDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {
    public static ChunkDTO ChunkDTO(Chunk chunk, int x, int y) {
        ChunkDTO chunkDTO = new ChunkDTO();

        List<CellDTO> cells = new ArrayList<>();

        TerrainType[][] chunkmap = chunk.getChunkmap();
        for (int i = 0; i < chunkmap.length; i++) {
            for (int j = 0; j < chunkmap[i].length; j++) {
                if (i == x && j == y)
                    cells.add(new CellDTO(chunkmap[i][j], RaceMapper.mapRace(Race.HUMAN)));
                else
                    cells.add(new CellDTO(chunkmap[i][j], null));
            }
        }

        chunkDTO.setCellDTOS(cells);
        chunkDTO.setGrid(true);
        chunkDTO.setCellsPerAxle(chunkmap.length);

        return chunkDTO;
    }
}
