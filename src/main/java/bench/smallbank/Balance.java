package bench.smallbank;

import bench.Tables;
import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Balance extends Transaction {
    static final String TXN_NAME = "Balance";
    private final String name;

    Balance(KvInterface kvi, String name) {
        super(kvi, TXN_NAME);

        this.name = name;
    }

    @Override
    public void inputGeneration() {
    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        int userId = Utils.getUserId(kvi, txn, name);
        Utils.getSavingBalance(kvi, txn, userId);
        Utils.getCheckingBalance(kvi, txn, userId);

        return commitTxn();
    }

}
