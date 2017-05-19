package info.openrpg.image.processing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChunkDTO {
    @JsonProperty(value = "cells_per_axle")
    private int cellsPerAxle;

    @JsonProperty(value = "grid")
    private boolean grid;

    @JsonProperty(value = "array")
    private List<CellDTO> cellDTOS;

    public void setCellsPerAxle(int cellsPerAxle) {
        this.cellsPerAxle = cellsPerAxle;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    public void setCellDTOS(List<CellDTO> cellDTOS) {
        this.cellDTOS = cellDTOS;
    }

    public int getCellsPerAxle() {
        return cellsPerAxle;
    }

    public boolean isGrid() {
        return grid;
    }

    public List<CellDTO> getCellDTOS() {
        return cellDTOS;
    }
}
