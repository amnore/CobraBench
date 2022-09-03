package bench.smallbank;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class WriteCheck extends Transaction {
    static final String TXN_NAME = "WriteCheck";
    private final String name;
    private final int amount;

    WriteCheck(KvInterface kvi, String name, int amount) {
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

        int checking = Utils.getCheckingBalance(kvi, txn, userId);
        int saving = Utils.getSavingBalance(kvi, txn, userId);

        if (checking + saving < amount) {
            kvi.set(txn, Utils.checkingKey(userId), Utils.checkingRow(userId, checking - amount - 1));
        } else {
            kvi.set(txn, Utils.checkingKey(userId), Utils.checkingRow(userId, checking - amount));
        }

        return commitTxn();
    }

}
