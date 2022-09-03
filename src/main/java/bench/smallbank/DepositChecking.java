package bench.smallbank;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class DepositChecking extends Transaction {
    static final String TXN_NAME = "DepositChecking";
    private final String name;
    private final int amount;

    DepositChecking(KvInterface kvi, String name, int amount) {
        super(kvi, TXN_NAME);
        assert amount >= 0;

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
        int balance = Utils.getCheckingBalance(kvi, txn, userId);
        kvi.set(txn, Utils.checkingKey(userId), Utils.checkingRow(userId, balance + amount));

        return commitTxn();
    }

}
