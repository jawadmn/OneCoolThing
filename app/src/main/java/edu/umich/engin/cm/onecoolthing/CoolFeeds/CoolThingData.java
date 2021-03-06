package edu.umich.engin.cm.onecoolthing.CoolFeeds;

/**
 * Created by jawad on 18/10/14.
 */
public class CoolThingData {
    private String id;

    private String title;
    private String subTitle;
    private String bodyText;

    private String paletteColor;
    private String imageURL;

    private String fullItemURL;
    private String tweetText;


    // Getter/setter methods:
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getBodyText() {
            return bodyText;
        }

        public void setBodyText(String bodyText) {
            this.bodyText = bodyText;
        }

        public String getPaletteColor() {
            return paletteColor;
        }

        public void setPaletteColor(String paletteColor) {
            this.paletteColor = paletteColor;
        }

        public String getImageURL() {
            return imageURL;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public String getFullItemURL() {
            return fullItemURL;
        }

        public void setFullItemURL(String fullItemURL) {
            this.fullItemURL = fullItemURL;
        }

        public String getTweetText() {
            return tweetText;
        }

        public void setTweetText(String tweetText) {
            this.tweetText = tweetText;
        }

    // end of getter/setter methods

    public CoolThingData() {}

    public CoolThingData(String id, String title, String bodyText){
        setId(id);
        setTitle(title);
        setBodyText(bodyText);
    }
}
