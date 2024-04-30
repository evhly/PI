public class DiffAlgorithm {

    char[] A; // One of two sequences to find the LCS of
    char[] B; // One of two sequences to find the LCS of
    int N; // length of sequence A
    int M; // length of sequence B
    int DMax; // maximum number of non-diagonal edges in a path from (0,0) to (N,M)
    int[][] VArr; // 2D array for holding V arrays for each value of D
    int shiftV; // offset to apply when accessing elements in V when using indexing for V [-MAX...MAX]

    /**
     * Initialize class variables
     *
     * @param A One of two sequences to find the LCS of
     * @param B One of two sequences to find the LCS of
     */
    private void init(char[] A, char[] B) {
        this.A = A;
        this.B = B;
        N = A.length;
        M = B.length;
        DMax = M + N;
        VArr = new int[DMax + 1][];
        shiftV = DMax;
    }

    /**
     * @param A
     * @param B
     * @return edit difference between A and B,
     * i.e. the smallest number of insertions and deletions to turn A into B
     */
    public int getDiff(char[] A, char[] B) {

        init(A, B);
        int[] V = new int[2 * DMax + 1]; // At the end of the line 44 loop, V[i] always holds the furthest reaching D-path on diagonal i-VShift, if such a path exists
        V[shiftV + 1] = 0;

        int x = 0; // x value of the furthest point on the path currently being traced
        int y = 0; // y value of the furthest point on the path currently being traced

        // calculate the furthest reaching D-path for each possible diagonal and store these values in VArr[D]
        for (int D = 0; D <= DMax; D++) {

            // for each k, find the furthest reaching D-path on diagonal k
            for (int k = -D; k <= D; k += 2) {
                // determine if the furthest D-path on diagonal k will be an extension of the furthest (D-1)-path on diagonal k+1 or diagonal k-1
                if (k == -D || (k != D && V[shiftV + k - 1] < V[shiftV + k + 1])) {
                    x = V[shiftV + k + 1]; // if diagonal k+1, the current path extends one unit vertically downward from V[shiftV + k + 1]
                } else {
                    x = V[shiftV + k - 1] + 1; // if diagonal k-1, the current path extends one unit horizontally rightward from V[shiftV + k - 1]
                }

                y = x - k; // calculate the value of y at the point at the end of the current path

                // take the longest snake extending from (x,y)
                while (x < N && y < M && A[x] == B[y]) {
                    x++;
                    y++;
                }

                // add x coordinate to V array to keep record of the furthest reaching D-path on diagonal k
                V[shiftV + k] = x;

                // return the edit distance if the point (N,M) has been reached, as at this point the path corresponding to the LCS has been fully traced
                if (x >= N && y >= M) {
                    return D;
                }
            }
            // Add the V array for value D to VArr
            VArr[D] = V.clone();
        }
        // this next line should never run
        return -1;
    }

}