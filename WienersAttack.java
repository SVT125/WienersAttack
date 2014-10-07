// Notes: RSA fully works, can rewrite all as one method, half-implemented the check for e*d. Start on Wiener's attack.

import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;

public class WienersAttack {
	public static void main( String[] args ) {
		RSA obj = new RSA(8);
		BigInteger x = obj.generateRandomZPrime();
		System.out.println("Encrypted...");
		System.out.println("Cipher: " + x);
		boolean isSecure = false;
		BigInteger d = obj.generateDecryptionExponent(obj.e,obj.phiN);
		BigInteger cipher = obj.encrypt(x);
		/*
		while( !isSecure ) {
			d = obj.generateDecryptionExponent(obj.e,obj.phiN);
			BigInteger check = cipher.pow((int)d.longValue()).modPow(obj.e,obj.n);
			System.out.println("The check is: " + check); // erase
			if(check.equals(cipher))
				isSecure = true;
		}
		*/
		System.out.println("Found d...");
		BigInteger key = obj.decrypt(cipher,d,obj.n);
		System.out.println("Decrypted...");
		System.out.println("The recovered key is: " + key);
	}
}

class RSA {
	private int numBits;
	public BigInteger e = new BigInteger("65537"), phiN, n;
	private BigInteger d;
	
	public RSA(int bits) {
		this.numBits = bits;
		Random r = new Random();
		BigInteger p = new BigInteger(bits,48,r), q = new BigInteger(bits,48,r);
		this.n = p.multiply(q);
		this.phiN = this.n.subtract(p).subtract(q).add(BigInteger.valueOf(1));	
	}
	// Returns a random element in Z*n.
	public BigInteger generateRandomZPrime() {
		BigInteger element = null;
		boolean foundPrimeElement = false;
		Random r = new Random();
		while(!foundPrimeElement) {
			BigInteger guess = new BigInteger(this.numBits,48,r);
			if(guess.compareTo(this.n) < 0) {
				element = guess;
				foundPrimeElement = true;
			}
		}
		return element;
	}
	
	// Encrypts the message with RSA.
	public BigInteger encrypt(BigInteger x) {
		BigInteger cipher = x.pow((int)this.e.longValue());
		return cipher;
	}
	
	// Decrypts the message.
	public BigInteger decrypt(BigInteger cipher, BigInteger d, BigInteger mod) {
		BigInteger message = cipher.modPow(d,mod);
		
		return message;
	}
	
	// Generates the decryption exponent.
	// Public access temporarily for test RSA.
	public BigInteger generateDecryptionExponent(BigInteger e, BigInteger mod) {
		BigInteger numerator = BigInteger.valueOf(1), decryptionExponent = null;
		int multiple = 0;
		boolean foundDecryptionExponent = false;
		while(!foundDecryptionExponent) {
			numerator = BigInteger.valueOf(1).add(mod.multiply(BigInteger.valueOf(multiple)));
			if(numerator.mod(e).equals(BigInteger.valueOf(0))) {
				decryptionExponent = numerator.divide(e);
				foundDecryptionExponent = true;
			} else
				multiple++;
		}
		return decryptionExponent;
	}
}
