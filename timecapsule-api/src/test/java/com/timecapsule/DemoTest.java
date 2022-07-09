package com.timecapsule;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DemoTest {

    @Test
    public void test() {
        var value = 1;

        assertThat(value).isEqualTo(2);
    }
}
