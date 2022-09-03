package bench.auctionmark.tables;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemImage {
    @PrimaryKey
    public final int ii_id;
    public final int ii_i_id;
    public final int ii_u_id;
    public final String ii_path;
}
