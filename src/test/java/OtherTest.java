import com.jackmanwu.jump.util.ColorUtil;
import com.jackmanwu.jump.util.ScreenshotUtil;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by JackManWu on 2018/1/10.
 */
public class OtherTest {
    @Test
    public void test() throws Exception {
        ScreenshotUtil.screenshot();
        BufferedImage image = ScreenshotUtil.getImage();
        int x = 0;
        int y = 0;

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                int red = ColorUtil.getRed(pixel);
                int green = ColorUtil.getGreen(pixel);
                int blue = ColorUtil.getBlue(pixel);
                /*if (54 < red && red < 60
                        && 53 < green && green > 63
                        && 95 < blue && blue > 110) {
                    System.out.println("hello");
                    System.out.println("扫描到了：" + i + "-" + j);
                }*/
                if (red == 53 && green == 54 && blue == 56) {
                    System.out.println("找到了: " + i + "-" + j);
                    x = j;
                    y = i;
                }

                if (red == 81 && green == 74 && blue == 61) {
                    System.out.println("扫描到分数");
                }
            }
        }
        System.out.println(x + "-" + y);
    }

    @Test
    public void test2(){
        int[] a = {2,4,12,0,1};
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
}
