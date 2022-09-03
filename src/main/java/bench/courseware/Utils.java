package bench.courseware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import bench.Tables;
import kv_interfaces.KvInterface;
import kvstore.exceptions.KvException;
import kvstore.exceptions.TxnException;

class Utils {
    static String studentKey(int id) {
        return Tables.encodeKey("student", new int[] { id });
    }

    static String courseKey(int id) {
        return Tables.encodeKey("course", new int[] { id });
    }

    static String openCourseKey(int id) {
        return Tables.encodeKey("openCourse", new int[] { id });
    }

    static String enrollKey(int studentId, int courseId) {
        return Tables.encodeKey("enroll", new int[] { studentId, courseId });
    }

    static String registeredStudentsKey(int id) {
        return Tables.encodeKey("registeredStudent", new int[] { id });
    }

    static Course getCourse(KvInterface kvi, Object txn, int courseId) throws TxnException, KvException {
        return new Course(Tables.decodeTable(kvi.get(txn, courseKey(courseId))));
    }

    static Student getStudent(KvInterface kvi, Object txn, int studentId) throws TxnException, KvException {
        return new Student(Tables.decodeTable(kvi.get(txn, studentKey(studentId))));
    }

    static boolean getCourseOpen(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        return !kvi.get(txn, openCourseKey(id)).equals("");
    }

    static boolean getStudentRegistered(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        return !kvi.get(txn, registeredStudentsKey(id)).equals("");
    }

    static boolean getEnrolled(KvInterface kvi, Object txn, int stduentId, int courseId) throws TxnException, KvException {
        return !kvi.get(txn, enrollKey(stduentId, courseId)).equals("");
    }

    static void openCourse(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        kvi.set(txn, openCourseKey(id), "1");
    }

    static void closeCourse(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        kvi.set(txn, openCourseKey(id), "");
    }

    static void registerStudent(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        kvi.set(txn, registeredStudentsKey(id), "1");
    }

    static void deregesterStudent(KvInterface kvi, Object txn, int id) throws TxnException, KvException {
        kvi.set(txn, registeredStudentsKey(id), "");
    }

    static void enrollCourse(KvInterface kvi, Object txn, int studentId, int courseId) throws TxnException, KvException {
        kvi.set(txn, enrollKey(studentId, courseId), "1");
    }
}
