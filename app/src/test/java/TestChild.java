import com.repertory.bean.UsersEntity;

/**
 * Created by Finderlo on 2016/11/5.
 */
public class TestChild extends TestGergc<UsersEntity> {

    public static void main(String[] args) {
        System.out.println(new TestChild().bindClass());
    }
}
