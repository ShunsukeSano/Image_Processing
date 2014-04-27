import static img.ImageManager.*;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
public class Blur {
    public static final int RANGE = 10;
    public static void main(String[] args) throws IOException {
        File file_read = new File("amane.jpg");
        BufferedImage read = ImageIO.read(file_read);                       //画像の読み込み
        int width = read.getWidth(), height = read.getHeight();             //画像のサイズを取得
        BufferedImage write =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        int average_count = 0;
        for(int y = 0; y < height; y++){                                    //全てのピクセルを見る
            for(int x = 0; x < width; x++){
                int r = 0;
                int g = 0;
                int b = 0;
                
                for(int i = -RANGE; i < RANGE+1; i++){                      //周囲10*10マスのRGBを平均
                    for(int j = -RANGE; j < RANGE+1; j++){
                        if(y+i >= 0 && y+i < height && x+j >= 0 && x+j < width){
                            int c = read.getRGB(x+j, y+i);
                            r += r(c);
                            g += g(c);
                            b += b(c);
                            average_count++;
                        }
                    }
                }

                int average_r = r / average_count;
                int average_g = g / average_count;
                int average_b = b / average_count;
                average_count = 0;

                int c_center = read.getRGB(x, y);
                r = (r(c_center) + average_r*99)/100;                       //対象のピクセルとその周りのピクセルどちらに比重をおくか、またその比
                g = (g(c_center) + average_g*99)/100;
                b = (b(c_center) + average_b*99)/100;

                int rgb = rgb(r,g,b);
                write.setRGB(x,y,rgb);
            }
        }
        File file_write = new File("RANGE10jpg");
        ImageIO.write(write, "jpg", file_write);
    }
}
