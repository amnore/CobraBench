package bench.courseware;

import bench.BenchUtils;
import bench.Transaction;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class LoadCourseware extends Transaction {
    static final String TXN_NAME = LoadCourseware.class.getName();
    private final int studentNum;
    private final int courseNum;

    LoadCourseware(KvInterface kvi, int studentNum, int courseNum) {
        super(kvi, TXN_NAME);
        this.studentNum = studentNum;
        this.courseNum = courseNum;
    }

    @Override
    public void inputGeneration() {
    }

    @Override
    public boolean doTansaction() throws KvException, TxnException {
        beginTxn();

        for (int id = 0; id < studentNum; id++) {
            kvi.insert(txn, Utils.studentKey(id), new Student(id, BenchUtils.getRandomValue(10)).getRow());
            kvi.insert(txn, Utils.registeredStudentsKey(id), "");
        }

        for (int id = 0; id < courseNum; id++) {
            kvi.insert(txn, Utils.courseKey(id),
                    new Course(id, BenchUtils.getRandomValue(10), BenchUtils.getRandomValue(10)).getRow());
            kvi.insert(txn, Utils.openCourseKey(id), "");
        }

        for (int studentId = 0; studentId < studentNum; studentId++) {
            for (int courseId = 0; courseId < courseNum; courseId++) {
                kvi.insert(txn, Utils.enrollKey(studentId, courseId), "");
            }
        }

        return commitTxn();
    }

}
