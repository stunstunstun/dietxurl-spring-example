package com.kakao.dietxurl.support;

/**
 * @author minhyeok
 */
public class Base62 {

    public static final char[] CODECS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static final int RADIX = 62;

    public static final int MAX_SIZE = 11;

    private Base62() {}

    public static final String encodeToString(Long id) {
        char[] buffer = new char[MAX_SIZE];
        int charIndex = buffer.length;
        for ( ; id > 0; id /= RADIX) {
            buffer[--charIndex] = CODECS[(int) Long.remainderUnsigned(id, RADIX)];
        }
        return new String(buffer, charIndex, buffer.length - charIndex);
    }
}