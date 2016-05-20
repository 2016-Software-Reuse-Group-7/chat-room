package TeamSeven.util.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class log {
    private static Logger logger = Logger.getLogger(log.class);

    public log(){
        PropertyConfigurator.configure("C:/Users/john/workspace2/file/src/log/log4j.properties");//加载.properties文件
        // System.out.println("This is println message.");
        // 记录debug级别的信息
        logger.debug("This is debug message.");
        // 记录info级别的信息
        logger.info("This is info message.");
        // 记录error级别的信息
        logger.error("This is error message.");
    }

}