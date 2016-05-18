package TeamSeven.common.message.server;

import TeamSeven.common.entity.Account;
import TeamSeven.common.enumerate.TransMessageTypeEnum;
import TeamSeven.common.message.BaseMessage;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhao on 2016/5/17.
 */
public class GroupMemberLoginMessage extends BaseMessage {

    protected Account account;
    protected List<HashMap<String,Boolean>> groupMembers;

    public GroupMemberLoginMessage(Account account){
        this.account = account;
    }

    @Override
    public TransMessageTypeEnum getType() {
        return TransMessageTypeEnum.LOGIN_STATUS;
    }

    public Account getAccount(){return this.account;}

    public List<HashMap<String,Boolean>> getGroupMembers(){return this.groupMembers;}
}
