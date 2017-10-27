package com.hades.leetcode;

import java.util.Arrays;

public class P190 {
    public int reverseBits(int n) {
        byte[] bs = new byte[32];
        byte[] bsn = Integer.toBinaryString(n).getBytes();
        System.arraycopy(bsn, 0, bs, 32 - bsn.length, bsn.length);
        Arrays.fill(bs, 0, 32 - bsn.length, (byte) '0');

        StringBuilder sb = new StringBuilder();
        for (int i = 31; i >= 0; i--) {
            sb.append((char) bs[i]);
        }
        return Integer.parseUnsignedInt(sb.toString(), 2);
    }

    public static void main(String[] args) {
        P190 p = new P190();
        System.out.println(p.reverseBits(43261596));
    }
}
