package info.openrpg.gameserver.utils;

public interface Filter<T> {
    boolean accept(T type);
}