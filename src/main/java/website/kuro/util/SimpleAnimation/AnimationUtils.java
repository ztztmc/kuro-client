package website.kuro.util.SimpleAnimation;

public class AnimationUtils {
    public static float calculateCompensation(float target, float current, double speed, long delta) {
        float diff = current - target;
        double add = (double)delta * (speed / 50.0);
        current = (double)diff > speed ? ((double)current - add > (double)target ? (float)((double)current - add) : target) : ((double)diff < -speed ? ((double)current + add < (double)target ? (float)((double)current + add) : target) : target);
        return current;
    }
}
