package com.drsports.jetpacklib;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        long mDirtyFlags = 0xFfffffffffffffffL;

        mDirtyFlags|=0x8L;

        System.out.println(mDirtyFlags&0x8L);

    }
}