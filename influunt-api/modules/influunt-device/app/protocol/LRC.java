package protocol;

public class LRC {
    public static byte calculateLRC(byte[] bytes) {
        byte LRC = 0;
        for (int i = 0; i < bytes.length - 1; i++) {
            LRC ^= bytes[i];
        }
        return LRC;
    }
}


