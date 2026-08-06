class Solution {
    public int reverse(int x) {
        int sum=0,a;
        while(x!=0){
            a = x%10;
            if (sum > Integer.MAX_VALUE / 10 ||
                (sum == Integer.MAX_VALUE / 10 && a > 7)) {
                return 0;
            }
            if (sum < Integer.MIN_VALUE / 10 ||
                (sum == Integer.MIN_VALUE / 10 && a < -8)) {
                return 0;
            }
            sum = sum*10 + a;
            x /= 10;
        }
        return sum;
    }
}