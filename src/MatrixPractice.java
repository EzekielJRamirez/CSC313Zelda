import java.util.Random;
import java.util.Vector;

public class MatrixPractice {

    public static void main(String[] args) {
//        Vector<Vector<Integer>> mat = new Vector<Vector<Integer>>();
//        mat.elementAt(0).addElement(66);
//        mat.elementAt(0).elementAt(0);
//        System.out.println(mat);
        rotateP();
//        mat.addElement(mat.elementAt(0).elementAt(0));
        // imagine the first element gets the first row, then the second
        // element gets the sixth col of that row

        // vector index starts at 0 and ends at size -1
    }

    public static void rotateP() {
        Vector<Vector<Integer>> ro = new Vector<>();
        Vector<Integer> temp = new Vector<>();
        Random r = new Random();
        for (int i = 0; i < 15; i++) {
            temp.addElement(r.nextInt(99) + 1);
        }
        ro.addElement(temp);
        System.out.println(ro);
    }
}
