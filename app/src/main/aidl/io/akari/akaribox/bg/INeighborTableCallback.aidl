package io.akari.akaribox.bg;

import io.akari.akaribox.bg.ParceledListSlice;

interface INeighborTableCallback {
    oneway void onNeighborTableUpdated(in ParceledListSlice entries);
}
