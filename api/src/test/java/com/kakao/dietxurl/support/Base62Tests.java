package com.kakao.dietxurl.support;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author minhyeok
 */
public class Base62Tests {

    @Test
    public void encode() {
        String encoded = Base62.encodeToString(Long.MAX_VALUE);
        assertThat(encoded.length()).isEqualTo(Base62.MAX_SIZE);
    }
}
