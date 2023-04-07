package com.ltyy.chatgpt.bean;

import java.util.List;

public class PromptRes {
//    {
//        "id": "chatcmpl-123",
//              "object": "chat.completion",
//              "created": 1677652288,
//              "choices": [{
//                "index": 0,
//                "message": {
//                "role": "assistant",
//                "content": "Hello there, how may I assist you today"
//               },
//        "finish_reason": "stop"
//    }],
//        "usage": {
//        "prompt_tokens": 9,
//                "completion_tokens": 12,
//                "total_tokens": 21
//    }
//    }

    private String id;
    private String object;
    private String created;
    private List<Prompt> choices;
    private String Usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<Prompt> getChoices() {
        return choices;
    }

    public void setChoices(List<Prompt> choices) {
        this.choices = choices;
    }

    public String getUsage() {
        return Usage;
    }

    public void setUsage(String usage) {
        Usage = usage;
    }
}
