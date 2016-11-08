package protocol;

import org.apache.commons.codec.binary.Hex;

import static java.security.spec.RSAKeyGenParameterSpec.F0;

public class LRC {
    public static byte calculateLRC(byte[] bytes) {
		byte LRC = 0;
		for (int i = 0; i < bytes.length - 1; i++) {
			LRC ^= bytes[i];
		}
		return LRC;
	}

    public static void main(String[] str){


        byte[] test = new byte[]{ 51,2,0,2,5,33,23,112,0,0,0,0,46,(byte)224,66,7,(byte)208,
            11,(byte)184,11,(byte)184,39,16,67,31, 64,0,0,0,0,39,16,
            (byte)196,0,0,19,(byte)136,11,(byte)184,39,16,37,31, 64,0,0,0,0,39,16,-95};
        System.out.println(Hex.encodeHexString(new byte[]{LRC.calculateLRC(test)}));
        System.out.println(Hex.encodeHexString(test));
    }
}


