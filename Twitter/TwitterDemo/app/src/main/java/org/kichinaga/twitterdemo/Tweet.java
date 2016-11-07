package org.kichinaga.twitterdemo;

import twitter4j.Status;

/**
 * Created by kichinaga on 2016/11/07.
 *
 * Tweetのモデルクラス
 */

public class Tweet {
    private String screenName;
    private String text;
    private String iconUrl;

    public Tweet(Status status) {
        setScreenName(status.getUser().getScreenName());
        setText(status.getText());
        setIconUrl(status.getUser().getProfileImageURLHttps());
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
