package org.fcproject.chatbot.api.domain.zsxq.model.res;

import org.bouncycastle.cert.ocsp.Req;
import org.fcproject.chatbot.api.domain.zsxq.model.req.ReqData;

/**
 * @Description: 请求问答接口结果
 * @Author: KyrieFc
 * @Date: 2023/5/7
 */

public class AnswerRes {
    private boolean succeeded;
    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
}
