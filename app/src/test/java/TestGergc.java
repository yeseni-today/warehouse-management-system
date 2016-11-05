import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Finderlo on 2016/11/5.
 */
public abstract class TestGergc<T> {

    public String bindClass() {
        Type sType = getClass().getGenericSuperclass();
        Type[] generics = ((ParameterizedType) sType).getActualTypeArguments();
        Class<T> mTClass = (Class<T>) (generics[0]);
        return mTClass.getName();
    }
}
