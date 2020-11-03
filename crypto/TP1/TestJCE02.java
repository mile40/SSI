import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

// --------------------------------------------------
// Exemple d'utilisation du JCE
// Generation d'une cle a partir d'un mot de passe (String)
// --------------------------------------------------

class TestJCE02 {

    private static byte[] salt = {(byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99};
    private static int count = 20;

    private static byte[] crypter(String password, String msg)	{
	// Indications:
	//    - utiliser "PBEWithMD5AndDES" pour le KeyFactory
	//    - utiliser "PBEWithMD5AndDES" pour le Cipher
	//    - penser a convertir la String en byte[] via la methode getBytes() avant de chiffrer

	byte[] buffer = null;
                
	try {
	    // Generation de la cle a partir du password
	    PBEParameterSpec paramSpec = new PBEParameterSpec(salt, count);
	    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
	    SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	    SecretKey passwordKey = kf.generateSecret(keySpec);
            
	    // Initialisation du Cipher avec la cle generee
	    Cipher enc = Cipher.getInstance("PBEWithMD5AndDES");
	    enc.init(Cipher.ENCRYPT_MODE, passwordKey, paramSpec);
            
	    // Chiffrement du message
	    buffer = enc.doFinal(msg.getBytes());
	} catch (Exception e) {
	    e.printStackTrace();
	}
        
	return buffer;
    }
	
    private static String decrypter(String password, byte[] buffer) {
	// Idem ci-dessus...

	byte[] tmp = null;
	
	try {
	    // Generation de la cle a partir du password
	    PBEParameterSpec paramSpec = new PBEParameterSpec(salt, count);
	    PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
	    SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	    SecretKey passwordKey = kf.generateSecret(keySpec);
            
	    // Initialisation du Cipher avec la cle generee
	    Cipher enc = Cipher.getInstance("PBEWithMD5AndDES");
	    enc.init(Cipher.DECRYPT_MODE, passwordKey, paramSpec);
            
	    // Dechiffrement du message
	    tmp = enc.doFinal(buffer);
	} catch (Exception e) {
	    e.printStackTrace();
	}
        
	return new String(tmp);
    }
	
    public static void main(String[] args) {
	if (args.length<2)
	    {
		System.out.println("Usage: java TestJCE02 <password> <message>");
		System.exit(0);
	    }
	
	String thePassword = args[0];
	String theMessage = args[1];
	
	System.out.println("message initial = \""+theMessage+"\"");
	byte[] buf = crypter(thePassword, theMessage);
	System.out.println("message crypte  = \""+buf+"\"");
	String msg = decrypter(thePassword, buf);
	System.out.println("message final   = \""+msg+"\"");
    }
}
