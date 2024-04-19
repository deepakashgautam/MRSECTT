package in.org.cris.mrsectt.util;

public class Encrypt {

    public static String encrypt(String value) {
        return EncryptionUtil.encode(value);
    }
    
    public static String decrypt(String value) {
        return EncryptionUtil.decode(value);
    }
    
    
    public static String encryptInt(Integer value) {
        return EncryptionUtil.encode(String.valueOf(value));
    }
    
    public static Integer decryptInt(String value) {
        return Integer.parseInt(EncryptionUtil.decode(value));
    }
   

}
