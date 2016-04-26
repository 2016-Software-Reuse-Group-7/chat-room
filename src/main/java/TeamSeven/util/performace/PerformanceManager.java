package TeamSeven.util.performace;

import java.io.IOException;

/**
 * Created by joshoy on 16/4/17.
 */
public interface PerformanceManager
{

    void initServerPm() throws IOException;
    void initClientPm( String clientName ) throws IOException;

    void clientAddMessage( String content ) throws Exception;
    void addReceivedMessage();
    void addIgnoredMessage();

    void startLog() throws IOException ;
    void endLog() throws IOException ;


}
