package bench.courseware;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class CloseCourse extends Transaction {
    static final String TXN_NAME = CloseCourse.class.getName();
    private final int courseId;

    CloseCourse(KvInterface kvi, int courseId) {
        super(kvi, TXN_NAME);

        this.courseId = courseId;
    }

    @Override
    public void inputGeneration() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        Utils.closeCourse(kvi, txn, courseId);

        return commitTxn();
    }
}
