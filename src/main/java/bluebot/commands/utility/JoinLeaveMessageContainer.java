package bluebot.commands.utility;

/**
 * @file JoinLeaveMessageContainer.java
 * @author Blue
 * @version 0.1
 * @brief Used to stock the messages posted by the bot on user events (join/leave)
 */
public class JoinLeaveMessageContainer {
    private String joinMessage;
    private boolean joinMention;
    private String leaveMessage;
    private boolean leaveMention;

    /*public JoinLeaveMessageContainer(String joinMessage, boolean joinMention, String leaveMessage, boolean leaveMention) {
        this.joinMessage = joinMessage;
        this.joinMention = joinMention;
        this.leaveMessage = leaveMessage;
        this.leaveMention = leaveMention;
    }*/

    public String getJoinMessage() {
        return joinMessage;
    }
    public boolean isJoinMention() {
        return joinMention;
    }
    public String getLeaveMessage() {
        return leaveMessage;
    }
    public boolean isLeaveMention() {
        return leaveMention;
    }
    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }
    public void setJoinMention(boolean joinMention) {
        this.joinMention = joinMention;
    }
    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }
    public void setLeaveMention(boolean leaveMention) {
        this.leaveMention = leaveMention;
    }
}
