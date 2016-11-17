
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Finderlo on 11/18/2016.
 */
public class TestLog {

    private static Logger logger = LogManager.getLogger(TestLog.class);

    public static void main(String[] args) {
        logger.debug("This is debug message");
        logger.error("error message");
        logger.info("info message");
    }
}
