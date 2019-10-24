package wjx.skill.util;

import java.util.Random;

/**
 * @author WangJX
 * @date 2019/2/15 11:42
 */
public class RandomUtil {

    public static Integer getRandom(Integer minimum, Integer maximum) {
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = rn.nextInt(maximum) % n;
        return minimum + i;
    }
}
