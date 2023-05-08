package org.fcproject.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @Description: GPT接口
 * @Author: KyrieFc
 * @Date: 2023/5/8
 */

public interface IOpenAI {

    String doChatGPT(String question) throws IOException;

 }
