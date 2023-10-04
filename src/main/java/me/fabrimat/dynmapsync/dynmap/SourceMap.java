package me.fabrimat.dynmapsync.dynmap;

import java.nio.file.Path;

public record SourceMap(Path path, boolean syncPlayers, boolean syncMarkers, boolean syncTiles, boolean syncDaytime) {
}
