package bench.courseware;

import java.util.stream.IntStream;

import bench.BenchUtils;
import bench.Benchmark;
import bench.Transaction;
import kv_interfaces.KvInterface;
import main.Config;

public class CoursewareBench extends Benchmark {
    private final int STUDENT_NUM;
    private final int COURSES_NUM;

    public CoursewareBench(KvInterface kvi) {
        super(kvi);

        STUDENT_NUM = Config.get().TWITTER_USERS_NUM;
        COURSES_NUM = Config.get().RUBIS_USERS_NUM;
    }

    @Override
    public Transaction[] preBenchmark() {
        return new Transaction[] { new LoadCourseware(kvi, STUDENT_NUM, COURSES_NUM) };
    }

    @Override
    public Transaction getNextTxn() {
        int dice = BenchUtils.getRandomInt(0, 100);
        if (dice < 20) {
            return new OpenCourse(kvi, BenchUtils.getRandomInt(0, COURSES_NUM));
        } else if (dice < 25) {
            return new CloseCourse(kvi, BenchUtils.getRandomInt(0, COURSES_NUM));
        } else if (dice < 45) {
            return new RegisterStudent(kvi, BenchUtils.getRandomInt(0, STUDENT_NUM));
        } else if (dice < 50) {
            return new DeregisterStudent(kvi, BenchUtils.getRandomInt(0, STUDENT_NUM));
        } else {
            return new Enroll(kvi, BenchUtils.getRandomInt(0, STUDENT_NUM), BenchUtils.getRandomInt(0, COURSES_NUM));
        }
    }

    @Override
    public void afterBenchmark() {
        // TODO Auto-generated method stub

    }

    @Override
    public String[] getTags() {
        return new String[] { OpenCourse.TXN_NAME, CloseCourse.TXN_NAME, RegisterStudent.TXN_NAME,
                DeregisterStudent.TXN_NAME, Enroll.TXN_NAME };
    }

}
