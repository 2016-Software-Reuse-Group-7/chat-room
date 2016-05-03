package TeamSeven.util.performace;

import java.io.IOException;

/**
 * Created by joshoy on 16/4/17.
 */
public interface PerformanceManager
{

    void clientAddMessage() throws Exception;
    void addReceivedMessage();
    void addIgnoredMessage();

    void startLog() throws IOException ;
    void endLog() throws IOException ;

}
