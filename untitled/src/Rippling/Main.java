package Rippling;

public class Main {

    public static void main(String[] args) {
        KVStoreTransactions kvStoreTransactions = new KVStoreTransactions();
        kvStoreTransactions.set("k1", "v1");

        assert kvStoreTransactions.get("k1").equals("v1");

        kvStoreTransactions.delete("k1");
        assert kvStoreTransactions.get("k1") == null;

        kvStoreTransactions.begin();
        kvStoreTransactions.set("k1", "t1v1");
        kvStoreTransactions.set("k2", "t1v2");

        assert kvStoreTransactions.get("k2").equals("t1v2");
        assert kvStoreTransactions.get("k1").equals("t1v1");

        kvStoreTransactions.delete("k1");

        assert kvStoreTransactions.get("k1") == null;

        kvStoreTransactions.commit();
        assert kvStoreTransactions.get("k1") == null;
        assert kvStoreTransactions.get("k2").equals("t1v2");


        kvStoreTransactions.begin();
        kvStoreTransactions.set("k1", "t2v1");
        kvStoreTransactions.set("k2", "t2v2");

        kvStoreTransactions.begin();
        assert kvStoreTransactions.get("k1").equals("t2v1");
        kvStoreTransactions.set("k1", "t3v1");
        assert kvStoreTransactions.get("k1").equals("t3v1");

        kvStoreTransactions.commit();
        assert kvStoreTransactions.get("k1").equals("t3v1");
        assert kvStoreTransactions.get("k2").equals("t2v2");

        kvStoreTransactions.commit();
        assert kvStoreTransactions.get("k1").equals("t3v1");
        assert kvStoreTransactions.get("k2").equals("t2v2");

        kvStoreTransactions.begin();
        kvStoreTransactions.set("k3", "t4v3");
        assert kvStoreTransactions.get("k3").equals("t4v3");
        kvStoreTransactions.begin();
        kvStoreTransactions.set("k4", "t5v4");
        assert kvStoreTransactions.get("k4").equals("t5v4");

        kvStoreTransactions.rollback();

        kvStoreTransactions.set("k5", "t4v5");
        assert kvStoreTransactions.get("k3").equals("t4v3");
        assert kvStoreTransactions.get("k5").equals("t4v5");
        kvStoreTransactions.rollback();

        assert kvStoreTransactions.get("k1").equals("t3v1");
        assert kvStoreTransactions.get("k2").equals("t2v2");

    }
}
