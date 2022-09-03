package bench.auctionmark.tables;

import java.util.Map;

import lombok.AllArgsConstructor;

/**
 * GlobalAttributeGroup
 */
@AllArgsConstructor
public class GlobalAttributeGroup {
    @PrimaryKey
    public final int gag_id;
    public final int gag_c_id;
    public final String gag_name;

    static String getKey(int gag_id) {
        return Utils.getKey(GlobalAttributeGroup.class, Map.of("gag_id", gag_id));
    }
}
