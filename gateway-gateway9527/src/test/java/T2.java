import java.time.ZonedDateTime;

/**
 * 得到yml文件中 predicates: - After=2022-10-26T16:21:25.390+08:00[Asia/Shanghai]
 * 表示：在这个时间之后访问：- Path 才有效果。
 */
public class T2 {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now();    //默认时区
        System.out.println(zbj);
        //2022-10-26T16:21:25.390+08:00[Asia/Shanghai]
    }
}
