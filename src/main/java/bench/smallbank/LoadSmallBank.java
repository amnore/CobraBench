package bench.smallbank;

import java.util.HashMap;

import bench.BenchUtils;
import bench.Tables;
import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class LoadSmallBank extends Transaction {
    static final String TXN_NAME = "LoadSmallBank";
    private final int userId;
    private final String name;

    LoadSmallBank(KvInterface kvi, int userId, String name) {
        super(kvi, TXN_NAME);
        this.userId = userId;
        this.name = name;
    }

	@Override
	public void inputGeneration() {
	}

	@Override
	public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        kvi.insert(txn, Utils.accountKey(name), Utils.accountRow(name, userId));
        kvi.insert(txn, Utils.savingKey(userId), Utils.savingRow(userId, 100));
        kvi.insert(txn, Utils.checkingKey(userId), Utils.checkingRow(userId, 100));

        return commitTxn();
	}

}
