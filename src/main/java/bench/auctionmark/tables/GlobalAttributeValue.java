package bench.auctionmark.tables;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GlobalAttributeValue {
    @PrimaryKey
    public final int gav_id;
    public final int gav_gag_id;
    public final String gav_name;

    static String getKey(int gav_id) {
        return Utils.getKey(GlobalAttributeValue.class, Map.of("gav_id", gav_id));
    }
}
