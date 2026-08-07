class Solution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> matrix = new ArrayList<>();
        for(int i=0;i<numRows;i++){
            matrix.add(new ArrayList<>());
            for(int j=0;j<=i;j++){
                if(j==0 || j==i){
                    matrix.get(i).add(1);
                }
                else{
                    matrix.get(i).add(matrix.get(i-1).get(j-1)+matrix.get(i-1).get(j));
                }
            }
        }
        return matrix;
    }
}