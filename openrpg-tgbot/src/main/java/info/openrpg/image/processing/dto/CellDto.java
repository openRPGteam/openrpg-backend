package info.openrpg.image.processing.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.openrpg.gameserver.enums.TerrainType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CellDto {
    @JsonProperty(value = "terrain_type")
    private TerrainType terrainType;
    @JsonProperty(value = "player")
    private String playerType;

    public CellDto(TerrainType terrainType, String playerType) {
        this.terrainType = terrainType;
        this.playerType = playerType;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(TerrainType terrainType) {
        this.terrainType = terrainType;
    }

    public String getPlayerType() {
        return playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}
