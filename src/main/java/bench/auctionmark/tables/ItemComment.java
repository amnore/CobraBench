package bench.auctionmark.tables;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemComment {
    public final int ic_id;
    public final int ic_i_id;
    public final int ic_u_id;
    public final int ic_buyer_id;
    public final int ic_date;
    public final String ic_question;
    public final String ic_response;
}
