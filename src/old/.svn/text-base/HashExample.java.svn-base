package old;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashExample {

	private static String convertToHex(byte[] data) {

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; i++) {

			int halfbyte = (data[i] >>> 4) & 0x0F;
			int two_halfs = 0;

			do {

				if ((0 <= halfbyte) && (halfbyte <= 9))

					buf.append((char) ('0' + halfbyte));

				else

					buf.append((char) ('a' + (halfbyte - 10)));

				halfbyte = data[i] & 0x0F;

			} while (two_halfs++ < 1);

		}
		return buf.toString();
	}

	public static String computeBoardHash(String boardRep, int rows, int columns, int salt)
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		String board = boardRep + rows + columns + salt;
		byte[] bytes = board.getBytes();
		md.update(bytes);
		return convertToHex(md.digest());
	}

	public static void main(String[] args) {

		String board = "000111000010000";
		String board2 = "011000110010110";
		try {
			String hash = computeBoardHash(board, 3, 5, 10);
			System.out.println(hash);
			String hash2 = computeBoardHash(board2, 5, 3, 10);
			System.out.println(hash2);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
}