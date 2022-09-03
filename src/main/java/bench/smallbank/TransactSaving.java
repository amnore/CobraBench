package bench.smallbank;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class TransactSaving extends Transaction {
    static final String TXN_NAME = "TransactSaving";
    private final String name;
    private final int amount;

    TransactSaving(KvInterface kvi, String name, int amount) {
        super(kvi, TXN_NAME);

        this.name = name;
        this.amount = amount;
    }

    @Override
    public void inputGeneration() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        int userId = Utils.getUserId(kvi, txn, name);
        int balance = Utils.getSavingBalance(kvi, txn, userId);
        if (balance + amount < 0) {
            return abortTxn();
        }

        kvi.set(txn, Utils.savingKey(userId), Utils.savingRow(userId, balance + amount));
        return commitTxn();
    }

}
