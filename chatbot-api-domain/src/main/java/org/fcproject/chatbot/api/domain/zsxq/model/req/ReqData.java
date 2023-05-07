package org.fcproject.chatbot.api.domain.zsxq.model.req;

/**
 * @Description:
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class ReqData {

    private final boolean silenced;
    public String text;

    public ReqData(String text, boolean silenced) {
        this.text = text;
        this.silenced = silenced;
    }
}
