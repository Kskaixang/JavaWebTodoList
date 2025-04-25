package hash;

import java.security.MessageDigest;

public class SimplePasswordHash {
	
	
 public static String hashPassword(String password) throws Exception{
	 //SHA-256 雜湊演算法
	 MessageDigest md = MessageDigest.getInstance("SHA-256");
	 byte[] hashBytes = md.digest(password.getBytes());
	 //轉成16進位字串
	 StringBuilder sb = new StringBuilder();
	 for(byte b : hashBytes) {
		 //%02x
		 /*
			0：補零（如果轉換結果不滿兩位，前面補 0）			
			2：總共兩位數			
			x：表示將數值轉為 16 進位小寫字母
		  */
		 sb.append(String.format("%02x", b));
	 }
	 return sb.toString();
	 
 }
 
  public static void main(String[] args)throws Exception{
	  String password = "1234";
	  String hash = hashPassword(password);
	  //%n換行
	  System.out.printf("password: %s hash: %s%n", password,hash);
	  System.out.printf("password: %s hash: %s", password,hash);
  }
 
}
