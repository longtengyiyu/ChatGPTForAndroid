package com.ltyy.chatgpt.bean;

import java.util.List;

public class PromptRes {
//    {
//        "id": "cmpl-6j9PhjAom9GyxBNCVacvckcVNKVZg",
//            "object": "text_completion",
//            "created": 1676218965,
//            "model": "text-davinci-003",
//            "choices": [
//        {
//            "text": "？\n\n我可以背诵50位圆周率：3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679。",
//                "index": 0,
//                "logprobs": null,
//                "finish_reason": "stop"
//        }
//  ],
//        "usage": {
//        "prompt_tokens": 21,
//                "completion_tokens": 73,
//                "total_tokens": 94
//    }
//    }

    private List<Prompt> choices;

    public List<Prompt> getChoices() {
        return choices;
    }

    public void setChoices(List<Prompt> choices) {
        this.choices = choices;
    }
}
