class Solution {
    public int longestSubsequence(int[] nums) {

        int xor = 0;

        // Find XOR of all elements
        for (int num : nums) {
            xor ^= num;
        }

        // If total XOR is non-zero,
        // we can take the whole array
        if (xor != 0) {
            return nums.length;
        }

        // Total XOR is zero.
        // If there is at least one non-zero element,
        // removing that element makes the XOR non-zero.
        for (int num : nums) {
            if (num != 0) {
                return nums.length - 1;
            }
        }

        // Every element is zero
        return 0;
    }
}