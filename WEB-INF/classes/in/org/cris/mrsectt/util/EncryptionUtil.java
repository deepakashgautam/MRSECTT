package in.org.cris.mrsectt.util;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class EncryptionUtil
{
  // blocked use temporarily
  
	private static final String ALGO= "PBEWithMD5AndDES";
	private static final byte[] SALT;
	private static final  KeySpec keySpec;
	private static final AlgorithmParameterSpec paramSpec;
	
	private final static int	ITERATION_COUNT	= 64;
	private final static int	KEY_LENGTH	= 256;
	static {
		Random randomno = new Random();
	      
		   // create byte array
		   //byte[] nbyte = new byte[8];
			byte[] nbyte = new byte[]{-59,29,56,39,23,56,-80,99};
		      
		   // put the next byte in the array
		   //randomno.nextBytes(nbyte);
		 SALT = nbyte;
		 keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT,KEY_LENGTH);
		 paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
		
	}
	private EncryptionUtil()
	{
	}

	public static String encode(String input)
	{
		//return input;
		if (input == null)
		{
			throw new IllegalArgumentException();
		}
		try
		{

			
			SecretKey key = SecretKeyFactory.getInstance(ALGO).generateSecret(keySpec);
			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

			byte[] enc = ecipher.doFinal(input.getBytes());

			String res = new String(Base64.encodeBase64(enc));
			// escapes for url
			res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A").replace("=", "~");
			//res= URLEncoder.encode(res,"UTF-8");

			return res;

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return "";

	}

	public static String decode(String token)
	{	
		//return token;
		if (token == null)
		{
			return null;
		}
		try
		{
			SecretKey key = SecretKeyFactory.getInstance(ALGO).generateSecret(keySpec);
			String input = token.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+').replace('~', '=');
			//String input = URLDecoder.decode(token,"UTF-8"); 
			byte[] dec = Base64.decodeBase64(input.getBytes());

			

			Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			byte[] decoded = dcipher.doFinal(dec);

			String result = new String(decoded);

			return result;

		}
		catch (Exception e)
		{
      // use logger in production code
			e.printStackTrace();
		}

		return null;
	}

}