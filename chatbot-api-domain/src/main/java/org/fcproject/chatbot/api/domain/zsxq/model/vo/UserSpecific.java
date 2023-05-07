package org.fcproject.chatbot.api.domain.zsxq.model.vo;

/**
 * @Description: userspecific
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class UserSpecific {
    private boolean liked;

    private boolean subscribed;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }
}
