class Solution {

    class Node {
        char leftChar;
        char rightChar;
        int prefix;
        int suffix;
        int max;
        int len;

        Node(char leftChar, char rightChar, int prefix,
             int suffix, int max, int len) {

            this.leftChar = leftChar;
            this.rightChar = rightChar;
            this.prefix = prefix;
            this.suffix = suffix;
            this.max = max;
            this.len = len;
        }
    }

    Node[] tree;
    char[] arr;

    public int[] longestRepeating(String s, String queryCharacters,
                                   int[] queryIndices) {

        arr = s.toCharArray();

        int n = arr.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] ans = new int[queryIndices.length];

        for (int i = 0; i < queryIndices.length; i++) {

            int index = queryIndices[i];

            arr[index] = queryCharacters.charAt(i);

            update(1, 0, n - 1, index);

            ans[i] = tree[1].max;
        }

        return ans;
    }

    void build(int node, int left, int right) {

        if (left == right) {

            tree[node] = new Node(
                arr[left],
                arr[left],
                1,
                1,
                1,
                1
            );

            return;
        }

        int mid = (left + right) / 2;

        build(node * 2, left, mid);
        build(node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    void update(int node, int left, int right, int index) {

        if (left == right) {

            tree[node] = new Node(
                arr[left],
                arr[left],
                1,
                1,
                1,
                1
            );

            return;
        }

        int mid = (left + right) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index);
        } else {
            update(node * 2 + 1, mid + 1, right, index);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    Node merge(Node a, Node b) {

        int len = a.len + b.len;

        int prefix = a.prefix;
        int suffix = b.suffix;

        int max = Math.max(a.max, b.max);

        if (a.rightChar == b.leftChar) {

            // Prefix can extend into b
            if (a.prefix == a.len) {
                prefix = a.len + b.prefix;
            }

            // Suffix can extend into a
            if (b.suffix == b.len) {
                suffix = b.len + a.suffix;
            }

            // Repeating sequence crossing the middle
            max = Math.max(max, a.suffix + b.prefix);
        }

        return new Node(
            a.leftChar,
            b.rightChar,
            prefix,
            suffix,
            max,
            len
        );
    }
}