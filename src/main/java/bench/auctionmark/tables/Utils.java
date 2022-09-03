package bench.auctionmark.tables;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Utils {
    private static final Map<Class<?>, Function<String, Object>> parsers = Map
            .ofEntries(Pair.of(Integer.class, Integer::valueOf));

    static <T> void insertObject(KvInterface kvi, Object txn, T obj) throws KvException, TxnException {
        String key = getKey(obj);
        for (Field f : obj.getClass().getDeclaredFields()) {
            try {
                kvi.insert(txn, getFieldKey(key, f.getName()), f.get(obj).toString());
            } catch (IllegalAccessException e) {
                throw new Error(e);
            }
        }
    }

    static <T> Object getField(KvInterface kvi, Object txn, Class<T> cls, String fieldName, String key)
            throws KvException, TxnException {
        try {
            Field field = cls.getDeclaredField(fieldName);
            Class<?> fieldCls = field.getClass();
            Function<String, Object> parser = parsers.get(fieldCls);

            String value = kvi.get(txn, key);
            return value == null ? null : parser.apply(value);
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    static <T> void setField(KvInterface kvi, Object txn, String fieldName, String key, Object value)
            throws KvException, TxnException {
        kvi.set(txn, getFieldKey(key, fieldName), value.toString());
    }

    private static String getFieldKey(String key, String fieldName) {
        return key + ':' + fieldName;
    }

    private static <T> String getKey(T obj) {
        Class<?> cls = obj.getClass();
        Map<String, Object> primaryKeys = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> f.getDeclaredAnnotation(PrimaryKey.class) != null).map(f -> {
                    try {
                        return Pair.of(f.getName(), f.get(obj));
                    } catch (IllegalAccessException e) {
                        throw new Error(e);
                    }
                }).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));

        return getKey(cls, primaryKeys);
    }

    static String getKey(Class<?> cls, Map<String, Object> primaryKeys) {
        String key = cls.getName() + ':';
        for (Entry<String, Object> e : primaryKeys.entrySet()) {
            key += e.getKey() + '=' + e.getValue().toString() + ',';
        }

        return key;
    }
}
