class Solution {
    public String convert(String s, int numRows) {
        if(numRows==1){
            return s;
        }
        int flag=0,j=0;
        StringBuilder[] str = new StringBuilder[numRows];
        StringBuilder ans = new StringBuilder();
        for(int i=0;i<numRows;i++){
            str[i] = new StringBuilder();
        }
        for(int i=0;i<s.length();i++){
            if(flag==0){
                str[j++].append(s.charAt(i));
                if(j==numRows){
                    j--;
                    flag=1;
                }
            }else{
                str[--j].append(s.charAt(i));
                if(j == 0){
                    j++;
                    flag=0;
                } 
            }
        }
        for(int i=0;i<numRows;i++){
            ans.append(str[i]);
        }
        return ans.toString();
    }
}