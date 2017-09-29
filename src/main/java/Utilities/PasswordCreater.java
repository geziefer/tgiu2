package Utilities;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCreater {

	public static void main(String[] args) {
		String pw;
		for (String s : args) {
			pw = DigestUtils.sha1Hex(s);
			System.out.println(pw);
		}
	}

}
