package TeamSeven.common.entity;

import org.java_websocket.WebSocket;

import java.io.Serializable;

/**
 * 其实就是对WebSocket进行封装, 用于Server端进行管理
 * Created by joshoy on 16/4/19.
 */
public final class Session implements Serializable {

    protected WebSocket conn;

    public Session(WebSocket conn) {
        this.conn = conn;
    }

    public WebSocket getConnection() {
        return this.conn;
    }

    @Override
    public int hashCode() {
        return this.conn.getRemoteSocketAddress().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Session) {
            return ((Session) obj).getConnection().hashCode() == this.conn.hashCode();
        }
        else {
            return false;
        }
    }
}
