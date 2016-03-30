
 public class flipString
 {
	private static final String normal = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String upsidedown = "ɐqɔpǝɟbɥıظʞןɯuodbɹsʇnʌʍxʎz‾'؛˙¿¡/\\,∀qϽᗡƎℲƃHIſʞ˥WNOԀὉᴚS⊥∩ΛMXʎZ0ƖᄅƐㄣϛ9ㄥ86";

    public static String flip(final String text)
    {
      String result = "";
      for (int i=text.length()-1;i>=0;i--) {
    	  result+= flipChar(text.charAt(i));
      }
      return result;
    }


    private static char flipChar(char c)
    {
    	return upsidedown.charAt(normal.indexOf(c));
    }

 }