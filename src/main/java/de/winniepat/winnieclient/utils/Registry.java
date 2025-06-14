package de.winniepat.winnieclient.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class Registry<T> {
    private final ConcurrentLinkedDeque<T> entries = new ConcurrentLinkedDeque<>();

    public boolean register(@NotNull T key) {
        return entries.add(key);
    }

    public boolean unregister(@NotNull T key) {
        return entries.remove(key);
    }

    public boolean contains(@NotNull T key) {
        return entries.contains(key);
    }
    public @Unmodifiable Collection<T> entries() {
        return Collections.unmodifiableCollection(this.entries);
    }
}
