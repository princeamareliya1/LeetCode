public class Solution {
    // Prime factor distributions for digits 0 to 9
    private static final int[] c2 = {0, 0, 1, 0, 2, 0, 1, 0, 3, 0};
    private static final int[] c3 = {0, 0, 0, 1, 0, 0, 1, 0, 0, 2};
    private static final int[] c5 = {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
    private static final int[] c7 = {0, 0, 0, 0, 0, 0, 0, 1, 0, 0};

    public String smallestNumber(String num, long t) {
        if (t <= 0) return "-1";

        // Step 1: Factorize t into prime factors 2, 3, 5, 7
        int r2 = 0, r3 = 0, r5 = 0, r7 = 0;
        long temp = t;
        while (temp % 2 == 0) { r2++; temp /= 2; }
        while (temp % 3 == 0) { r3++; temp /= 3; }
        while (temp % 5 == 0) { r5++; temp /= 5; }
        while (temp % 7 == 0) { r7++; temp /= 7; }

        // If t contains prime factors other than 2, 3, 5, or 7
        if (temp > 1) return "-1";

        int n = num.length();

        // Locate the index of the first '0' in num
        int firstZero = n;
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                firstZero = i;
                break;
            }
        }

        // p2[i], p3[i], p5[i], p7[i] store required prime factors after matching prefix num[0...i-1]
        int[] p2 = new int[n + 1];
        int[] p3 = new int[n + 1];
        int[] p5 = new int[n + 1];
        int[] p7 = new int[n + 1];

        p2[0] = r2; p3[0] = r3; p5[0] = r5; p7[0] = r7;

        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            p2[i + 1] = Math.max(0, p2[i] - c2[d]);
            p3[i + 1] = Math.max(0, p3[i] - c3[d]);
            p5[i + 1] = Math.max(0, p5[i] - c5[d]);
            p7[i + 1] = Math.max(0, p7[i] - c7[d]);
        }

        // Case 1a: Check if num itself is valid
        if (firstZero == n && p2[n] == 0 && p3[n] == 0 && p5[n] == 0 && p7[n] == 0) {
            return num;
        }

        // Case 1b: Try matching prefix of length i (from min(n-1, firstZero) down to 0)
        int maxI = Math.min(n - 1, firstZero);
        for (int i = maxI; i >= 0; i--) {
            int curDigit = num.charAt(i) - '0';
            for (int d = curDigit + 1; d <= 9; d++) {
                int rem2 = Math.max(0, p2[i] - c2[d]);
                int rem3 = Math.max(0, p3[i] - c3[d]);
                int rem5 = Math.max(0, p5[i] - c5[d]);
                int rem7 = Math.max(0, p7[i] - c7[d]);
                int remLen = n - 1 - i;

                if (minLen(rem2, rem3, rem5, rem7) <= remLen) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(d);
                    sb.append(buildSuffix(remLen, rem2, rem3, rem5, rem7));
                    return sb.toString();
                }
            }
        }

        // Case 2: Construct smallest number of length > n
        int targetLen = Math.max(n + 1, minLen(r2, r3, r5, r7));
        return buildSuffix(targetLen, r2, r3, r5, r7);
    }

    // Minimum digits needed to cover required prime factors
    private int minLen(int r2, int r3, int r5, int r7) {
        r2 = Math.max(0, r2);
        r3 = Math.max(0, r3);
        r5 = Math.max(0, r5);
        r7 = Math.max(0, r7);

        // Option 1: Use 8s and 9s (plus 2, 4, 3 if needed)
        int opt1 = (r2 + 2) / 3 + (r3 + 1) / 2;
        // Option 2: Use one '6' (covers one 2 and one 3), then 8s and 9s
        int opt2 = 1 + (Math.max(0, r2 - 1) + 2) / 3 + (Math.max(0, r3 - 1) + 1) / 2;

        return r5 + r7 + Math.min(opt1, opt2);
    }

    // Greedily builds the smallest zero-free string of given length
    private String buildSuffix(int len, int r2, int r3, int r5, int r7) {
        StringBuilder sb = new StringBuilder();
        for (int pos = 0; pos < len; pos++) {
            for (int d = 1; d <= 9; d++) {
                int nr2 = Math.max(0, r2 - c2[d]);
                int nr3 = Math.max(0, r3 - c3[d]);
                int nr5 = Math.max(0, r5 - c5[d]);
                int nr7 = Math.max(0, r7 - c7[d]);
                int remLen = len - 1 - pos;

                if (minLen(nr2, nr3, nr5, nr7) <= remLen) {
                    sb.append(d);
                    r2 = nr2;
                    r3 = nr3;
                    r5 = nr5;
                    r7 = nr7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}