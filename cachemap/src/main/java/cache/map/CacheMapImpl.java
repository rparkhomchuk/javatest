package cache.map;

import cache.utils.Clock;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Cache Map implementation backed by {@link LinkedHashMap}
 */
public class CacheMapImpl<KeyType, ValueType> implements CacheMap<KeyType, ValueType> {
    public static final long ETERNAL = 0L;

    private long ttl = ETERNAL;
    private Map<KeyType, ValueWrapper> map = new LinkedHashMap<>();

    @Override
    public void setTimeToLive(long timeToLive) {
        if (timeToLive < 0) {
            throw new IllegalArgumentException("Time to live must be positive or 0 if eternal.");
        }
        ttl = timeToLive;
    }

    @Override
    public long getTimeToLive() {
        return ttl;
    }

    @Override
    public ValueType put(KeyType key, ValueType value) {
        if (key == null) {
            throw new IllegalArgumentException("Key may not be null.");
        }
        //remove previous mapping to save insertion order
        ValueWrapper<ValueType> removed = map.remove(key);

        //mapping with null value should be removed
        if (value != null) {
            map.put(key, new ValueWrapper<>(value, Clock.getTime()));
        }
        if (removed != null && isNotExpired(removed)) {
            return removed.getValue();
        }

        return null;
    }

    @Override
    public void clearExpired() {
        Iterator<Map.Entry<KeyType, ValueWrapper>> iterator = map.entrySet().iterator();
        boolean foundNotExpired = false;

        //iterate over the map and remove expired while first not expired will be found
        while (!foundNotExpired && iterator.hasNext()) {
            Map.Entry<KeyType, ValueWrapper> entry = iterator.next();
            if (isExpired(entry.getValue())) {
                iterator.remove();
            } else {
                foundNotExpired = true;
            }
        }
    }

    private boolean isExpired(ValueWrapper key) {
        if (ttl != ETERNAL && Clock.getTime() - key.getCreationTime() > ttl) {
            return true;
        }

        return false;
    }

    private boolean isNotExpired(ValueWrapper key) {
        return !isExpired(key);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsKey(KeyType key) {
        ValueWrapper<ValueType> valueWrapper = map.get(key);

        if (valueWrapper != null && isNotExpired(valueWrapper)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean containsValue(ValueType value) {
        //null values are removed from map
        if (value == null) {
            return false;
        }
        Iterator<ValueWrapper> iterator = map.values().iterator();

        while (iterator.hasNext()) {
            ValueWrapper<ValueType> next = iterator.next();

            if (next != null && value.equals(next.getValue()) && isNotExpired(next)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ValueType get(KeyType key) {
        if (key == null) {
            throw new IllegalArgumentException("Key may not be null.");
        }
        ValueWrapper<ValueType> valueWrapper = map.get(key);

        if (valueWrapper != null && isNotExpired(valueWrapper)) {
            return valueWrapper.getValue();
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        Iterator<Map.Entry<KeyType, ValueWrapper>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<KeyType, ValueWrapper> next = iterator.next();

            if (next != null && isNotExpired(next.getValue())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ValueType remove(KeyType key) {
        ValueWrapper<ValueType> removed = map.remove(key);
        return removed != null ? removed.getValue() : null;
    }

    @Override
    public int size() {
        final int[] size = {0};

        map.forEach(new BiConsumer<KeyType, ValueWrapper>() {
            @Override
            public void accept(KeyType keyType, ValueWrapper valueWrapper) {
                if (valueWrapper != null && isNotExpired(valueWrapper)) {
                    size[0] = size[0] + 1;
                }
            }
        });

        return size[0];
    }

    /**
     * Extend map value with creation time.
     * Creation time not used in hash and equals methods.
     *
     * @param <T> type of the value
     */
    class ValueWrapper<T> {
        private T value;
        private long creationTime;

        public ValueWrapper(T value) {
            this.value = value;
        }

        public ValueWrapper(T value, long creationTime) {
            this.value = value;
            this.creationTime = creationTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ValueWrapper)) return false;

            ValueWrapper<?> that = (ValueWrapper<?>) o;

            return value != null ? value.equals(that.value) : that.value == null;

        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }

        public T getValue() {
            return value;
        }

        public long getCreationTime() {
            return creationTime;
        }
    }

}
