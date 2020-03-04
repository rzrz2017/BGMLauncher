package com.dsn.andy.bgmlauncher.bean;

/**
 * Created by Administrator on 2018/3/23.
 */

public class SpeechHelp {
    public String  prompt;
    public String   answer;
    public int  icon;

    public SpeechHelp(int icon,String prompt,String answer){
        this.icon=icon;
        this.prompt=prompt;
        this.answer=answer;
    }

    public String getprompt() {
        return prompt;
    }

    public void setprompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
