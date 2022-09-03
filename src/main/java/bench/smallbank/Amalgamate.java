package bench.smallbank;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Amalgamate extends Transaction {
    static final String TXN_NAME = "Amalgamate";
    private final String from, to;

    Amalgamate(KvInterface kvi, String from, String to) {
        super(kvi, TXN_NAME);

        this.from = from;
        this.to = to;
    }

    @Override
    public void inputGeneration() {
    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        if (from.equals(to)) {
            return true;
        }

        beginTxn();

        int fromId = Utils.getUserId(kvi, txn, from);
        int toId = Utils.getUserId(kvi, txn, to);

        int fromChecking = Utils.getCheckingBalance(kvi, txn, fromId);
        int fromSaving = Utils.getSavingBalance(kvi, txn, fromId);
        int toChecking = Utils.getCheckingBalance(kvi, txn, toId);
        kvi.set(txn, Utils.checkingKey(toId), Utils.checkingRow(toId, fromChecking + fromSaving + toChecking));
        kvi.set(txn, Utils.savingKey(fromId), Utils.savingRow(fromId, 0));
        kvi.set(txn, Utils.checkingKey(fromId), Utils.checkingRow(fromId, 0));

        return commitTxn();
    }

}
