
import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

public class VectorQuantization {

    static int newh = 0;
    static int neww = 0;

    public void Compress(String path, int CBW, int CBH, int CBS) {

        ArrayList<block> save = turnImageToBlocks(path, CBW, CBH);
        block a = getAVG(save, 0);
        ArrayList<block> codeBook = split(save, a, CBS);
        inhancement(save, codeBook);
        int counteer = 0;
        int[][] compressedImage = new int[neww][newh];
        for (int j = 0; j < neww; j++) {
            for (int k = 0; k < newh; k++) {
                compressedImage[j][k] = save.get(counteer).code;
                counteer++;

            }
        }

        saveFile(compressedImage, codeBook, "decompressed" + path);
        System.out.println(path + " Compress succeded!");
    }

    public void ReadFile(String Dpath) {
        ArrayList<block> codebook = new ArrayList<>();
        try {

            FileInputStream f = new FileInputStream(Dpath);
            DataInputStream d = new DataInputStream(f);
            int codebook_size = d.readInt();
            int block_w = d.readInt();
            int block_h = d.readInt();
            int code = 0;
            for (int m = 0; m < codebook_size; m++) {
                code = d.readInt();
                block a = new block(block_w, block_h, code);
                int arr2[] = new int[block_w * block_h];
                for (int i = 0; i < block_w * block_h; i++) {
                    int x = d.readInt();
                    arr2[i] = x;
                }
                int counter = 0;
                int arr3[][] = new int[block_w][block_h];
                for (int i = 0; i < block_w; i++) {
                    for (int j = 0; j < block_h; j++) {
                        arr3[i][j] = arr2[counter++];
                    }

                }
                a.setArrayofblock(arr3);
                codebook.add(a);
            }

            neww = d.readInt();
            newh = d.readInt();

            int compressed[][] = new int[neww][newh];
            int comp1[] = new int[neww * newh];
            for (int i = 0; i < neww * newh; i++) {
                int x = d.readInt();
                comp1[i] = x;
            }
            int count = 0;
            for (int i = 0; i < neww; i++) {
                for (int j = 0; j < newh; j++) {
                    compressed[i][j] = comp1[count++];

                }
            }
            f.close();
            d.close();

            Decompress(compressed, codebook, Dpath);

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void Decompress(int[][] compressedImage, ArrayList<block> codeBook, String Dpath) {

        try {
            int w = codeBook.get(0).w;
            int h = codeBook.get(0).h;
            int height = newh * h;
            int width = neww * w;
            BufferedImage constructed = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Queue<block> con = new LinkedList<block>();

            for (int i = 0; i < neww; i++) {
                for (int j = 0; j < newh; j++) {
                    for (int k = 0; k < codeBook.size(); k++) {
                        block nn = new block();
                        if (compressedImage[i][j] == codeBook.get(k).code) {
                            nn = codeBook.get(k);
                            con.add(nn);
                            break;
                        }
                    }

                }
            }

            for (int i = 0; i < width; i = i + w) {
                for (int j = 0; j < height; j = j + h) {
                    block x = con.poll();
                    for (int l = 0; l < w; l++) {
                        for (int m = 0; m < h; m++) {
                            int ff = 255;
                            int pix = x.arr[l][m];
                            int p = (ff << 24) | (pix << 16) | (pix << 8) | pix;
                            constructed.setRGB(i + l, j + m, p);
                        }
                    }

                }
            }

            File f = new File(Dpath);
            ImageIO.write(constructed, "png", f);
            System.out.println(Dpath + " Decompress succeded!");
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void saveFile(int[][] compressedImage, ArrayList<block> codebook, String Path) {
        try {
            FileOutputStream f = new FileOutputStream(Path);
            DataOutputStream d = new DataOutputStream(f);
            d.writeInt(codebook.size());
            d.writeInt(codebook.get(0).w);
            d.writeInt(codebook.get(0).h);

            for (int k = 0; k < codebook.size(); k++) {
                d.writeInt(codebook.get(k).code);
                for (int i = 0; i < codebook.get(k).w; i++) {
                    for (int j = 0; j < codebook.get(k).h; j++) {
                        d.writeInt(codebook.get(k).arr[i][j]);
                    }
                }
            }

            d.writeInt(neww);
            d.writeInt(newh);
            for (int i = 0; i < neww; i++) {
                for (int j = 0; j < newh; j++) {
                    d.writeInt(compressedImage[i][j]);
                }
            }

            f.close();
        } catch (Exception e) {

        }
    }

    public void inhancement(ArrayList<block> save, ArrayList<block> codeBook) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < codeBook.size(); j++) {
                block a = getAVG(save, codeBook.get(j).code);
                codeBook.set(j, a);
            }

        }
    }

    public static ArrayList<block> split(ArrayList<block> save, block AVG, int CBS) {
        ArrayList<block> codeBook = new ArrayList<block>();
        ArrayList<block> newCodeBook = new ArrayList<block>();
        codeBook.add(AVG);
        int cooode = 0;
        while (codeBook.size() < CBS) {
            for (int i = 0; i < codeBook.size(); ++i) {
                ArrayList<block> splitors = sp(codeBook.get(i));
                for (block a : splitors) {
                    newCodeBook.add(a);
                    cooode = a.code;
                }
                if (i < codeBook.size() - 1) {
                    codeBook.get(i + 1).code = cooode + 1;
                }

            }
            codeBook.clear();
            for (block a : newCodeBook) {
                codeBook.add(a);
            }
            newCodeBook.clear();
            getSbilings(codeBook, save);
            for (int i = 0; i < codeBook.size(); i++) {
                block a = getAVG(save, codeBook.get(i).code);
                codeBook.set(i, a);
            }

        }
        return codeBook;

    }

    public static ArrayList<block> sp(block AVG) {
        ArrayList<block> neW = new ArrayList<block>();
        block a1 = new block(AVG.w, AVG.h, AVG.code);
        block a2 = new block(AVG.w, AVG.h, AVG.code + 1);
        for (int i = 0; i < AVG.w; i++) {
            for (int j = 0; j < AVG.h; j++) {
                a1.arr[i][j] = AVG.arr[i][j] - 1;
                a2.arr[i][j] = AVG.arr[i][j] + 1;
            }
        }
        neW.add(a1);
        neW.add(a2);
        return neW;
    }

    public static void getSbilings(ArrayList<block> codeBook, ArrayList<block> save) {
        for (int i = 0; i < save.size(); i++) {
            double sum1 = 0;
            int code = -1;
            for (int m = 0; m < codeBook.size(); m++) {
                double sum2 = 0;
                for (int j = 0; j < save.get(i).w; j++) {
                    for (int k = 0; k < save.get(i).h; k++) {
                        sum2 += Math.pow(codeBook.get(m).arr[j][k] - save.get(i).arr[j][k], 2);
                    }
                }
                sum2 = Math.sqrt(sum2);
                if (m == 0) {
                    sum1 = sum2;
                }
                if (sum1 >= sum2) {
                    sum1 = sum2;
                    code = codeBook.get(m).code;
                }
            }
            save.get(i).code = code;
        }
    }

    public static block getAVG(ArrayList<block> save, int cod) {
        int w = save.get(0).w;
        int h = save.get(0).h;
        int counter = 0;
        block a = new block(w, h, cod);

        for (int i = 0; i < save.size(); i++) {
            if (save.get(i).code == cod) {
                counter++;
            }
        }

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int sum = 0;
                for (int k = 0; k < save.size(); k++) {
                    if (save.get(k).code == cod) {
                        sum += save.get(k).arr[i][j];
                    }
                }
                if (counter == 0) {
                    a.arr[i][j] = 0;
                } else {
                    sum /= counter;
                    a.arr[i][j] = sum;
                }
            }
        }

        return a;
    }

    public static ArrayList<block> turnImageToBlocks(String path, int CBW, int CBH) {

        try {
            BufferedImage img = null;
            File inp = new File(path);
            img = ImageIO.read(inp);
            int height = img.getHeight();
            int width = img.getWidth();
            int arr[][] = new int[width][height];
            int gray;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    int p = img.getRGB(j, i);
                    int a = (p >> 24) & 0xff;
                    int r = (p >> 16) & 0xff;
                    int g = (p >> 8) & 0xff;
                    int b = p & 0xff;
                    arr[j][i] = b;
                }
            }
            int w = CBW;
            int h = CBH;
            while (width % w != 0) {
                w++;
            }
            while (height % h != 0) {
                h++;
            }
            newh = height / h;
            neww = width / w;
            ArrayList<block> save = new ArrayList<block>();
            int a[][] = new int[w][h];
            for (int i = 0; i < width; i = i + w) {
                for (int j = 0; j < height; j = j + h) {
                    block cb = new block(w, h, 0);
                    for (int l = 0; l < w; l++) {
                        for (int m = 0; m < h; m++) {
                            a[l][m] = arr[l + i][m + j];
                        }
                    }
                    cb.setArrayofblock(a);
                    save.add(cb);
                }
            }
            return save;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
