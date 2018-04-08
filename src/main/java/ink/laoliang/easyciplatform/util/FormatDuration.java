package ink.laoliang.easyciplatform.util;

public class FormatDuration {

    /**
     * @param ms
     * @return *天 *小时 *分 *秒
     */
    public static String byMillisecond(long ms) {
        long days = ms / (1000 * 60 * 60 * 24);
        long hours = (ms % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (ms % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (ms % (1000 * 60)) / 1000;
        if (days > 0) {
            return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
        }
        if (hours > 0) {
            return hours + "h " + minutes + "m " + seconds + "s";
        }
        if (minutes > 0) {
            return minutes + "m " + seconds + "s";
        }
        if (seconds > 0) {
            return seconds + "s";
        }
        return (float) ms / 1000 + "s";
    }
}
