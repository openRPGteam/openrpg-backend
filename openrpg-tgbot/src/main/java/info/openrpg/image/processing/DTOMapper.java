package info.openrpg.image.processing;

import info.openrpg.gameserver.enums.Race;
import info.openrpg.gameserver.enums.TerrainType;
import info.openrpg.gameserver.model.actors.AbstractActor;
import info.openrpg.gameserver.model.actors.Player;
import info.openrpg.gameserver.model.world.Chunk;
import info.openrpg.image.processing.dto.CellDto;
import info.openrpg.image.processing.dto.ChunkDto;

import java.util.ArrayList;
import java.util.List;

public class DTOMapper {
    public static ChunkDto ChunkDTO(Chunk chunk, int x, int y) {
        ChunkDto chunkDto = new ChunkDto();

        List<CellDto> cells = new ArrayList<>();

        TerrainType[][] chunkmap = chunk.getChunkmap();
        for (int i = 0; i < chunkmap.length; i++) {
            for (int j = 0; j < chunkmap[i].length; j++) {
                if (i == x && j == y)
                    cells.add(new CellDto(chunkmap[i][j], RaceMapper.mapRace(Race.HUMAN)));
                else
                    cells.add(new CellDto(chunkmap[i][j], null));
            }
        }

        chunkDto.setCellDtos(cells);
        chunkDto.setGrid(true);
        chunkDto.setCellsPerAxle(chunkmap.length);

        return chunkDto;
    }

    public static ChunkDto createChunkDtoByPlayers(Chunk chunk, List<Player> players) {
        ChunkDto chunkDto = new ChunkDto();

        List<CellDto> cells = new ArrayList<>();

        TerrainType[][] chunkmap = chunk.getChunkmap();
        for (int i = 0; i < chunkmap.length; i++) {
            for (int j = 0; j < chunkmap[i].length; j++) {
                cells.add(new CellDto(chunkmap[i][j], null));
            }
        }

        players.stream()
                .map(AbstractActor::getCurLocation)
                .forEach(location -> cells
                        .get(location.getX()*chunkmap.length + location.getY())
                        .setPlayerType(RaceMapper.mapRace(Race.HUMAN)));
        chunkDto.setCellDtos(cells);
        chunkDto.setGrid(true);
        chunkDto.setCellsPerAxle(chunkmap.length);

        return chunkDto;
    }
}
