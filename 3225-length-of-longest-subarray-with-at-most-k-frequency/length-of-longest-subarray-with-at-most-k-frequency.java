import java.util.HashMap;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        
        HashMap<Integer, Integer> frequency = new HashMap<>();
        
        int left = 0;
        int largestSubArray = 0;

        for (int right = 0; right < nums.length; right++) {
            
            // Increase frequency of nums[right]
            frequency.put(nums[right], 
                frequency.getOrDefault(nums[right], 0) + 1);

            // If frequency becomes greater than k,
            // move left until the window becomes good
            while (frequency.get(nums[right]) > k) {
                
                frequency.put(nums[left], 
                    frequency.get(nums[left]) - 1);
                
                left++;
            }

            // Current window length
            int currentLength = right - left + 1;

            largestSubArray = Math.max(largestSubArray, currentLength);
        }

        return largestSubArray;
    }
}