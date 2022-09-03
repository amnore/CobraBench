package bench.courseware;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class RegisterStudent extends Transaction {
    static final String TXN_NAME = RegisterStudent.class.getName();
    private final int studentId;

    RegisterStudent(KvInterface kvi, int studentId) {
        super(kvi, TXN_NAME);

        this.studentId = studentId;
    }

    @Override
    public void inputGeneration() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        Utils.registerStudent(kvi, txn, studentId);

        return commitTxn();
    }
}
