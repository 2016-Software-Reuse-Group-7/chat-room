package TeamSeven.server.session;

import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;

import java.security.Key;
import java.util.Map;
import java.util.Set;

/**
 * Created by joshoy on 16/4/19.
 */
public abstract class SessionManager {

    /* 当前连接中的Session */
    protected Set<Session> sessions;
    /* Session 密钥/公钥 */
    protected Map<Session, Key> sessionKeyMap;
    /* 加密方式 */
    protected Map<Session, EncryptTypeEnum> sessionEncryptTypeMap;

    /**
     * 检查当前连接到server的session中是否存在该session
     * @param session
     * @return
     */
    public abstract boolean hasSession(Session session);

    /**
     * 添加session
     * @param session
     */
    public abstract void addSession(Session session);

    /**
     * 移除session
     * @param session
     */
    public abstract void removeSession(Session session);

    /**
     * 设置session的密钥/公钥
     * @param session
     * @param key
     */
    public abstract void setSessionKey(Session session, Key key);

    /**
     * 设置session的加密类型
     * @param session
     * @param type
     */
    public abstract void setSessionEncryptType(Session session, EncryptTypeEnum type);

    public abstract EncryptTypeEnum getSessionEncryptType(Session session);

    public abstract Key getSessionEncryptKey(Session session);
}
