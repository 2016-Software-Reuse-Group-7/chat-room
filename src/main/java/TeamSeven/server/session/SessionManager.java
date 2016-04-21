package TeamSeven.server.session;

import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;

import java.security.Key;
import java.security.PublicKey;
import java.util.Map;
import java.util.Set;

/**
 * Created by joshoy on 16/4/19.
 */
public abstract class SessionManager {

    /* 当前连接中的Session */
    protected Set<Session> sessions;
    /* Session 密钥/接收后解密用私钥 */
    protected Map<Session, Key> sessionKeyMap;
    /* Session向client发送用公钥 */
    protected Map<Session, PublicKey> sessionPublicKeyMap;
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
     * 设置session的密钥/私钥
     * @param session
     * @param key
     */
    public abstract void setSessionKey(Session session, Key key);

    /**
     * 获取session的公钥(向客户端发送时用)
     * @param session
     */
    public abstract PublicKey getSessionPublicKey(Session session);

    /**
     * 设置session的公钥(向客户端发送时用)
     * @param session
     * @param key
     */
    public abstract void setSessionPublicKey(Session session, PublicKey key);

    /**
     * 设置session的加密类型
     * @param session
     * @param type
     */
    public abstract void setSessionEncryptType(Session session, EncryptTypeEnum type);

    /**
     * 获取session的加密类型
     * @param session
     * @return
     */
    public abstract EncryptTypeEnum getSessionEncryptType(Session session);

    /**
     * 获取session的密钥/私钥
     * @param session
     * @return
     */
    public abstract Key getSessionEncryptKey(Session session);
}
