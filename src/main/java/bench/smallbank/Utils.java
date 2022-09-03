package bench.smallbank;

import java.util.HashMap;

import bench.Tables;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Utils {
    static String accountRow(String name, int userId) {
        HashMap<String, Object> row = new HashMap<>();
        row.put("name", name);
        row.put("userId", userId);
        return Tables.encodeTable(row);
    }

    static String savingRow(int userId, int balance) {
        HashMap<String, Object> row = new HashMap<>();
        row.put("userId", userId);
        row.put("balance", balance);
        return Tables.encodeTable(row);
    }

    static String checkingRow(int userId, int balance) {
        return savingRow(userId, balance);
    }

    static String accountKey(String name) {
        return Tables.encodeKey("account", new String[] { name });
    }

    static String savingKey(int userId) {
        return Tables.encodeKey("saving", new int[] { userId });
    }

    static String checkingKey(int userId) {
        return Tables.encodeKey("checking", new int[] { userId });
    }

    static int getUserId(KvInterface kvi, Object txn, String name) throws KvException, TxnException {
        return (int) Tables.decodeTable(kvi.get(txn, accountKey(name))).get("userId");
    }

    static int getSavingBalance(KvInterface kvi, Object txn, int userId) throws KvException, TxnException {
        return (int) Tables.decodeTable(kvi.get(txn, savingKey(userId))).get("balance");
    }

    static int getCheckingBalance(KvInterface kvi, Object txn, int userId) throws KvException, TxnException {
        return (int) Tables.decodeTable(kvi.get(txn, checkingKey(userId))).get("balance");
    }
}
