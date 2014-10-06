import java.math.BigInteger;
import java.util.Random;

public class WienersAttack {
	public static void main( String[] args ) {
		
	}
}

class RSA {
	private int numBits;
	public BigInteger e = new BigInteger("65537");
	private BigInteger d,nm phiN;
	
	public RSA(int bits) {
		this.numBits = bits;
		Random r = new Random();
		BigInteger p = new BigInteger(bits,48,r), q = new BigInteger(bits,48,r);
		this.n = p.multiply(q);
		this.phiN = p.subtract("1").multiply.(q.subtract("1"));	
	}
	
	// Encrypts the message with RSA.
	public BigInteger encrypt(String message) {
		BigInteger messageInteger = this.translateAsInteger(message);
		BigInteger cipher = messageInteger.modPow(this.e,this.n);
		return cipher;
	}
	
	// Decrypts the message.
	public String decrypt(BigInteger cipher, BigInteger d, BigInteger mod) {
		BigInteger message = cipher.modPow(d,mod);
	}
	
	// Generates the decryption exponent.
	private BigInteger generateDecryptionExponent(BigInteger e, BigInteger mod) {
		BigInteger numerator = 1, decryptionExponent;
		int multiple = 0;
		boolean foundDecryptionExponent = false;
		while(!foundDecryptionExponent) {
			numerator = 1 + mod.multiply(multiple);
			if(numerator.mod(e) == 0) {
				decryptionExponent = numerator.divide(e);
			} else
				multiple++;
		}
		return decryptionExponent;
	}
	
	// Goes per character in the message, translating each to an integer then concatenating.
	private BigInteger translateAsInteger(String message) {
		String buffer = "";
		char[] messageArray = message.toCharArray();
		for( char c : messageArray )
			buffer = buffer + Character.getNumericValue(c);

		BigInteger messageInteger = new BigInteger(buffer);
		return messageInteger;
	}
	
	// Goes per digit in the message, translating each to a character then concatenating to a string.
	private String translateAsString(BigInteger message) {
		char[] messageArray = message.toString().toCharArray();
		for( char c : messageArray ) {
			
		}
	}
}