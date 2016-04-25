package TeamSeven.common.performance;

/**
 * Created by tina on 16/4/25.
 */
public class MessageCount
{
    private boolean serverFlag;
    private int sendMessageCount;
    private int receivedMessageCount;
    private int ignoredMessageCount;

    public MessageCount( boolean serverFlag )
    {
        this.serverFlag = serverFlag;
        sendMessageCount = 0;
        receivedMessageCount = 0;
        ignoredMessageCount = 0;
    }

    public String toString()
    {
        if( serverFlag )
        {
            System.out.println( "一共接收消息" + receivedMessageCount + "条, 忽略消息" + ignoredMessageCount + "条" );
            return "一共接收消息" + receivedMessageCount + "条, 忽略消息" + ignoredMessageCount + "条\n";
        }else
        {
            System.out.println( "共发送消息" + sendMessageCount + "条, 被接收消息" + receivedMessageCount + "条, 被忽略消息" + ignoredMessageCount + "条" );
            return "共发送消息" + sendMessageCount + "条, 被接收消息" + receivedMessageCount + "条, 被忽略消息" + ignoredMessageCount + "条\n";
        }
    }

    public int getSendMessageCount() {
        return sendMessageCount;
    }

    public void setSendMessageCount(int sendMessageCount) {
        this.sendMessageCount = sendMessageCount;
    }

    public void addSendMessageCount(int number) {
        this.sendMessageCount = sendMessageCount + number;
    }

    public int getReceivedMessageCount() {
        return receivedMessageCount;
    }

    public void setReceivedMessageCount(int receivedMessageCount) {
        this.receivedMessageCount = receivedMessageCount;
    }

    public void addReceivedMessageCount(int number) {
        this.receivedMessageCount = receivedMessageCount + number;
    }

    public int getIgnoredMessageCount() {
        return ignoredMessageCount;
    }

    public void setIgnoredMessageCount(int ignoredMessageCount) {
        this.ignoredMessageCount = ignoredMessageCount;
    }

    public void addIgnoredMessageCount(int number) {
        this.ignoredMessageCount = ignoredMessageCount + number;
    }

}
