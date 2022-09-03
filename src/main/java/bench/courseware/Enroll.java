package bench.courseware;

import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Enroll extends Transaction {
    static final String TXN_NAME = Enroll.class.getName();
    private final int studentId;
    private final int courseId;

    Enroll(KvInterface kvi, int studentId, int courseId) {
        super(kvi, TXN_NAME);

        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public void inputGeneration() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        if (Utils.getStudentRegistered(kvi, txn, studentId) && Utils.getCourseOpen(kvi, txn, courseId)) {
            Utils.enrollCourse(kvi, txn, studentId, courseId);
        }

        return commitTxn();
    }
}
