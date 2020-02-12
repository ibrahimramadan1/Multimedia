
public class block {

    int code;
    int arr[][];
    int w, h;

    block() {

    }

    block(int wi, int height, int cod) {
        w = wi;
        h = height;
        code = cod;
        arr = new int[w][h];
    }

    public void setArrayofblock(int a[][]) {
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                arr[i][j] = a[i][j];
            }
        }
    }

    public void print() {
        System.out.println(code);
        System.out.println();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                System.out.print(arr[i][j] + "    ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------------------------");
    }

}
