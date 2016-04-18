package TeamSeven.server.session;

import TeamSeven.common.entity.Session;
import TeamSeven.common.enumerate.EncryptTypeEnum;

import java.security.Key;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by joshoy on 16/4/19.
 */
public class SessionManagerImpl extends SessionManager {

    public SessionManagerImpl() {
        this.sessions = new HashSet<Session>();
        this.sessionKeyMap = new HashMap<Session, Key>();
        this.sessionEncryptTypeMap = new HashMap<Session, EncryptTypeEnum>();
    }

    @Override
    public boolean hasSession(Session session) {
        return this.sessions.contains(session);
    }

    @Override
    public void addSession(Session session) {
        if (this.hasSession(session)) {
            return;
        }
        else {
            this.sessions.add(session);
        }
    }

    @Override
    public void removeSession(Session session) {
        this.sessions.remove(session);
        if (this.sessionKeyMap.containsKey(session)) {
            this.sessionKeyMap.remove(session);
        }
        if (this.sessionEncryptTypeMap.containsKey(session)) {
            this.sessionEncryptTypeMap.remove(session);
        }
    }

    @Override
    public void setSessionKey(Session session, Key key) {
        this.sessionKeyMap.put(session, key);
    }

    @Override
    public void setSessionEncryptType(Session session, EncryptTypeEnum type) {
        this.sessionEncryptTypeMap.put(session, type);
    }

    @Override
    public EncryptTypeEnum getSessionEncryptType(Session session) {
        return this.sessionEncryptTypeMap.get(session);
    }

    @Override
    public Key getSessionEncryptKey(Session session) {
        return this.sessionKeyMap.get(session);
    }
}
