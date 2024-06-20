package douyin.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinUtil {
    public static String toPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        char[] arr = chinese.toCharArray();
        for (char c : arr) {
            if ((c >= 0x4e00) && (c <= 0x9fbb)) { // 中文字符范围
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
                if (pinyinArray != null && pinyinArray.length > 0) {
                    pinyin.append(pinyinArray[0]);
                }
            } else {
                pinyin.append(c);
            }
        }
        return pinyin.toString();
    }
}