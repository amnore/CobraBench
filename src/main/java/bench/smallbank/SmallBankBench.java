package bench.smallbank;

import java.util.function.Supplier;
import java.util.stream.IntStream;

import bench.BenchUtils;
import bench.Benchmark;
import bench.Transaction;
import kv_interfaces.KvInterface;
import main.Config;

public class SmallBankBench extends Benchmark {
    private final int USER_NUM;
    private final String[] names;

    public SmallBankBench(KvInterface kvi) {
        super(kvi);

        USER_NUM = Config.get().TWITTER_USERS_NUM;
        names = IntStream.range(0, USER_NUM).mapToObj(x -> BenchUtils.getRandomValue(10)).toArray(String[]::new);
    }

    @Override
    public Transaction[] preBenchmark() {
        return IntStream.range(0, USER_NUM).mapToObj(userId -> new LoadSmallBank(kvi, userId, names[userId]))
                .toArray(Transaction[]::new);
    }

    @Override
    public Transaction getNextTxn() {
        Supplier<String> randomName = () -> names[BenchUtils.getRandomInt(0, USER_NUM)];
        Supplier<Integer> randomAmount = () -> BenchUtils.getRandomInt(0, 10);

        switch (BenchUtils.getRandomInt(0, 5)) {
        case 0:
            return new Balance(kvi, randomName.get());
        case 1:
            return new DepositChecking(kvi, randomName.get(), randomAmount.get());
        case 2:
            return new TransactSaving(kvi, randomName.get(), randomAmount.get());
        case 3:
            return new Amalgamate(kvi, randomName.get(), randomName.get());
        case 4:
            return new WriteCheck(kvi, randomName.get(), randomAmount.get());
        default:
            throw new Error("unreachable");
        }
    }

    @Override
    public void afterBenchmark() {
    }

    @Override
    public String[] getTags() {
        return new String[] { Balance.TXN_NAME, DepositChecking.TXN_NAME, TransactSaving.TXN_NAME, Amalgamate.TXN_NAME,
                WriteCheck.TXN_NAME };
    }

}
