package info.openrpg.image.processing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ChunkDto {
    @JsonProperty(value = "cells_per_axle")
    private int cellsPerAxle;

    @JsonProperty(value = "grid")
    private boolean grid;

    @JsonProperty(value = "array")
    private List<CellDto> cellDtos;

    public void setCellsPerAxle(int cellsPerAxle) {
        this.cellsPerAxle = cellsPerAxle;
    }

    public void setGrid(boolean grid) {
        this.grid = grid;
    }

    public void setCellDtos(List<CellDto> cellDtos) {
        this.cellDtos = cellDtos;
    }

    public int getCellsPerAxle() {
        return cellsPerAxle;
    }

    public boolean isGrid() {
        return grid;
    }

    public List<CellDto> getCellDtos() {
        return cellDtos;
    }
}
