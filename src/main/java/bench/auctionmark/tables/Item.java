package bench.auctionmark.tables;

import java.util.Map;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Item {
    @PrimaryKey
    public final int i_id;
    public final int i_u_id;
    public final int i_c_id;
    public final String i_name;
    public final String i_description;
    public final String i_user_attributes;
    public final double i_initial_price;
    public final double i_current_price;
    public final int i_num_bids;
    public final int i_num_images;
    public final int i_num_global_attrs;
    public final long i_start_date;
    public final long i_end_date;
    public final int i_status;

    public static Transaction newItem(KvInterface kvi, Object txn, int i_id, int u_id, int c_id, String name,
            String description, double initial_price, double reserve_price, double buy_now, String attributes,
            int[] gag_ids, int[] gav_ids, String[] images, long start_date, long end_date) {

        return new Transaction(kvi, "new-item") {

            @Override
            public void inputGeneration() {
            }

            @Override
            public boolean doTansaction() throws KvException, TxnException {
                beginTxn();

                String description = "";
                assert gag_ids.length == gav_ids.length;
                for (int i = 0; i < gag_ids.length; i++) {
                    description += (String) Utils.getField(kvi, txn, GlobalAttributeGroup.class, "gag_name",
                            GlobalAttributeGroup.getKey(gag_ids[i])) + ' '
                            + (String) Utils.getField(kvi, txn, GlobalAttributeValue.class, "gav_name",
                                    GlobalAttributeValue.getKey(gav_ids[i]));
                }

                Item item = new Item(i_id, u_id, c_id, name, description, attributes, initial_price, 0, 0,
                        images.length, gav_ids.length, start_date, end_date, 0);
                Utils.insertObject(kvi, txn, item);

                for (int i = 0; i < images.length; i++) {
                    int ii_id = (i << 28) | (i_id & 0xfffffff);
                    ItemImage image = new ItemImage(ii_id, i_id, u_id, images[i]);
                    Utils.insertObject(kvi, txn, image);
                }

                int u_balance = (int) Utils.getField(kvi, txn, User.class, "u_balance", User.getKey(u_id));
                Utils.setField(kvi, txn, "u_balance", User.getKey(u_id), u_balance - 1);

                return commitTxn();
            }
        };
    }
}
