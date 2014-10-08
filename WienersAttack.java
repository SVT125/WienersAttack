// Notes: RSA fully works, can rewrite all as one method, half-implemented the check for e*d. Start on Wiener's attack.

import java.math.BigInteger;
import java.util.Random;
import java.util.Arrays;

public class WienersAttack {
	public static void main( String[] args ) {
		TestRSA obj = new TestRSA(16);
		WienersAttack.attack(obj);
	}

	// Runs Wiener's attack on the given RSA object.
	// Can later set a range for the multiples, as well as number of iterations.
	public static void attack(TestRSA rsa) {
		BigInteger x = rsa.generateRandomZPrime();
		System.out.println("x: " + x);
		BigInteger d = null;
		boolean isSuitableExponent = false;
		while(!isSuitableExponent) {
			d = rsa.generateDecryptionExponent(rsa.e,rsa.phiN);
			if( d.longValue() <=(Math.pow(rsa.n.longValue(),0.25)/3)) {
				isSuitableExponent = true;
			}
		}
		System.out.println("Found eligible d");
		BigInteger cipher = rsa.encrypt(x);
		BigInteger key = rsa.decrypt(cipher,d,rsa.n);
		System.out.println("The decrypted x: " + key);
		System.out.println("-------------------------------------------------");
		
		boolean exponentFound = false;
		double fraction = rsa.n.longValue() / rsa.e.longValue();
		int multiple = 1;
		
		BigInteger attackKey = null;
		while(!exponentFound) {
			long guess = Math.round(multiple * fraction);
			if(d.equals(BigInteger.valueOf(guess)))
				System.out.println("Found match!");
			/*
			BigInteger guessedKey = cipher.modPow(BigInteger.valueOf(guess),rsa.n);
			System.out.println(guessedKey);
			if(x.equals(guessedKey)) {
				exponentFound = true;
				attackKey = guessedKey;
			}
			*/
			// run the guessed decryption exponent on the ciphertext; recall c = m^e, so m = c^d since m^(e*d) = m^1 = m.
		}
		System.out.println("The attack recovered x: " + attackKey);
	}
}

class TestRSA {
	private int numBits;
	public BigInteger message = null; // should not be public in normal RSA
	public BigInteger e = new BigInteger("65537"), phiN, n;
	private BigInteger d;
	
	public TestRSA(int bits) {
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
		d = decryptionExponent;
		return decryptionExponent;
	}
}
