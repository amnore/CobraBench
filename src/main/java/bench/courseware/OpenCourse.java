package bench.courseware;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class OpenCourse extends Transaction {
    static final String TXN_NAME = OpenCourse.class.getName();
    private final int courseId;

    OpenCourse(KvInterface kvi, int courseId) {
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

        Utils.openCourse(kvi, txn, courseId);

        return commitTxn();
    }
}
