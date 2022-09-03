package bench.auctionmark.tables;

import java.util.Map;

import bench.Tables;
import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class User {
    @PrimaryKey
    public final int u_id;
    public final int u_rating;
    public final double u_balance;
    public final long u_created;
    public final int u_r_id;

    public static Transaction newUser(KvInterface kvi, Object txn, int u_id, int u_r_id) {
        User user = new User(u_id, 0, 0, System.nanoTime(), u_r_id);

        return new Transaction(kvi, "new-user") {

            @Override
            public void inputGeneration() {
            }

            @Override
            public boolean doTansaction() throws KvException, TxnException {
                beginTxn();
                Utils.insertObject(kvi, txn, user);
                return commitTxn();
            }
        };
    }

    static String getKey(int u_id) {
        return Utils.getKey(User.class, Map.of("u_id", u_id));
    }
}
