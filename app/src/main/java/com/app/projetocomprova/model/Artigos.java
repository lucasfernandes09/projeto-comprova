package com.app.projetocomprova.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class Artigos implements Parcelable {

    private String term;
    private String date;
    private String title;
    private String link;
    private String img;
    private String content;
    private String status;
    private String verifiedContent;
    private Elements contentMain;
    private List<String> listImgInvest;
    private List<String> listImgVerif;
    private String shareFacebook;
    private String shareTwitter;

    public Artigos() {
    }

    protected Artigos(Parcel in) {
        term = in.readString();
        date = in.readString();
        title = in.readString();
        link = in.readString();
        img = in.readString();
        content = in.readString();
        status = in.readString();
        shareFacebook = in.readString();
        shareTwitter = in.readString();
    }

    public static final Creator<Artigos> CREATOR = new Creator<Artigos>() {
        @Override
        public Artigos createFromParcel(Parcel in) {
            return new Artigos(in);
        }

        @Override
        public Artigos[] newArray(int size) {
            return new Artigos[size];
        }
    };

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifiedContent() {
        return verifiedContent;
    }

    public void setVerifiedContent(String verifiedContent) {
        this.verifiedContent = verifiedContent;
    }

    public Elements getContentMain() {
        return contentMain;
    }

    public void setContentMain(Elements contentMain) {
        this.contentMain = contentMain;
    }

    public List<String> getListImgInvest() {
        return listImgInvest;
    }

    public void setListImgInvest(List<String> listImgInvest) {
        this.listImgInvest = listImgInvest;
    }

    public List<String> getListImgVerif() {
        return listImgVerif;
    }

    public void setListImgVerif(List<String> listImgVerif) {
        this.listImgVerif = listImgVerif;
    }

    public String getShareFacebook() {
        return shareFacebook;
    }

    public void setShareFacebook(String shareFacebook) {
        this.shareFacebook = shareFacebook;
    }

    public String getShareTwitter() {
        return shareTwitter;
    }

    public void setShareTwitter(String shareTwitter) {
        this.shareTwitter = shareTwitter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(term);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(img);
        dest.writeString(content);
        dest.writeString(status);
        dest.writeString(shareFacebook);
        dest.writeString(shareTwitter);
    }
}
