import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

// --------------------------------------------------
// Exemple d'utilisation du JCE
// Generation d'une paire de cles publique/privee
// --------------------------------------------------

class TestJCE01 {
    static KeyPair pair;

    private static void genererCles() {
	// Indications:
	//    - utiliser l'algo "SHA1PRNG" pour generer le nombre aleatoire
	//    - utiliser l'algo "RSA" pour generer la paire de cles

	try {
		//generation de la seed
	    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	    
	    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
	    keyGen.initialize(1024,random);
	    //genere la clé de paires
	    pair = keyGen.generateKeyPair();
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
    }
	
    private static byte[] crypter(String msg) {
	// Indications:
	//    - utiliser la combinaison "RSA/ECB/PKCS1Padding" pour creer le Cipher
	//    - penser a convertir la String en byte[] via la methode getBytes() avant de chiffrer

	try {
		//instanciatin du Cipher
	    Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    c.init(Cipher.ENCRYPT_MODE, pair.getPrivate());
		//chiffrement du message
		c.update(msg.getBytes());
		//recuperation du resultat sous forme d'un byte[]
	    return c.doFinal();
	} catch (java.security.NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (javax.crypto.NoSuchPaddingException e) {
	    e.printStackTrace();
	} catch (java.security.InvalidKeyException e) {
	    e.printStackTrace();
	} catch (javax.crypto.IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (javax.crypto.BadPaddingException e) {
	    e.printStackTrace();
	}

	// S'il y a eu des erreurs...
	return new byte[0];
    }
	
    private static String decrypter(byte[] buffer) {
	// Idem ci-dessus...

	try {
		//instanciation du cipher en mode decryptage
	    Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
	    c.init(Cipher.DECRYPT_MODE, pair.getPublic());

		//dechiffrement du msg
	    c.update(buffer);
	    return new String(c.doFinal());
	} catch (java.security.NoSuchAlgorithmException e) {
	    e.printStackTrace();
	} catch (javax.crypto.NoSuchPaddingException e) {
	    e.printStackTrace();
	} catch (java.security.InvalidKeyException e) {
	    e.printStackTrace();
	} catch (javax.crypto.IllegalBlockSizeException e) {
	    e.printStackTrace();
	} catch (javax.crypto.BadPaddingException e) {
	    e.printStackTrace();
	}

	// S'il y a eu des erreurs...
	return "error";
    }
	
    public static void main(String[] args) {
	if (args.length<1)
	    {
		System.out.println("Usage: java TestJCE01 <message>");
		System.exit(0);
	    }
	
	String theMessage = args[0];
	genererCles();
	
	System.out.println("message initial = \""+theMessage+"\"");
	byte[] buf = crypter(theMessage);
	System.out.println("message crypte  = \""+buf+"\"");
	String msg = decrypter(buf);
	System.out.println("message final   = \""+msg+"\"");
    }
}