package bench.auctionmark;

import bench.Benchmark;
import bench.Transaction;
import kv_interfaces.KvInterface;

public class AuctionMarkBench extends Benchmark {
    private final KvInterface kvi;

    public AuctionMarkBench(KvInterface kvi) {
        this.kvi = kvi;
    }

    @Override
    public Transaction[] preBenchmark() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Transaction getNextTxn() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void afterBenchmark() {
        // TODO Auto-generated method stub

    }

    @Override
    public String[] getTags() {
        // TODO Auto-generated method stub
        return null;
    }
}
