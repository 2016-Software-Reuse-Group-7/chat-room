package TeamSeven.common.exception;

/**
 * Created by joshoy on 16/4/17.
 */
public class ChatRoomBaseException extends Exception {

    protected String errMsg;

    public ChatRoomBaseException() {

    }

    public ChatRoomBaseException(String msg) {
        this.errMsg = msg;
    }

    public String getErrorMessage() {
        return this.errMsg;
    }

    public void printErrorMessage() {
        System.err.println("[!!! EXCEPTION !!!]: " + this.errMsg);
    }

}
