package utilities;

import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
 /**
  * This class is the MD5 hashing implementation used for the approval 
  * keys
  * @author Nick Barnwell
  *
  */
public class MD5 { 
	/**
	 * Converts the digest created by the genMD5 function to a 
	 * hexadecimal string representation.
	 * @param data ByteArray of the digest
	 * @return String MD5 representation of digest
	 */
    private static String convertToHexadecimal(byte[] data) { 
        StringBuffer buffer = new StringBuffer();
        for (int character = 0; character < data.length; character++) { 
            int halfbyte = (data[character] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buffer.append((char) ('0' + halfbyte));
                else 
                    buffer.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[character] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buffer.toString();
    }
    private static String convertToHexadecimal2(byte[] data) { 
        StringBuffer buffer = new StringBuffer();
        for (int character = 0; character < data.length; character++) { 
            int halfbyte = (data[character] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buffer.append((char) ('0' + halfbyte));
                else 
                    buffer.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[character] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buffer.toString();
    } 
 
    public static String genMD5(String text) 
    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHexadecimal(md5hash);
    }
    
    public static void main(String args[]) {
    	MD5 m = new MD5();
    	String test = "test";
    	
    }
} 
 