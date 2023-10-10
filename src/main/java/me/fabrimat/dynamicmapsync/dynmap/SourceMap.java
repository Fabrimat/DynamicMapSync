package me.fabrimat.dynamicmapsync.dynmap;

import java.nio.file.Path;

public record SourceMap(Path path, boolean syncPlayers, boolean syncMarkers, boolean syncTiles, boolean syncDaytime) {
}
