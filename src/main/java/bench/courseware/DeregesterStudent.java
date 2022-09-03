package bench.courseware;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class DeregisterStudent extends Transaction {
    static final String TXN_NAME = DeregisterStudent.class.getName();
    private final int studentId;

    DeregisterStudent(KvInterface kvi, int studentId) {
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

        Utils.deregesterStudent(kvi, txn, studentId);

        return commitTxn();
    }
}
