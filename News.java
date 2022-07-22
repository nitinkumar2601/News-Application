package com.example.android.dekhonews;

public class News
{
    private String mTitle;
    private String mUrl;
    private String mThumbnail;
    private String mPublished;

    public News(String title, String url, String thumbnail, String publish)
    {
        mTitle = title;
        mUrl = url;
        mThumbnail = thumbnail;
        mPublished  = publish;
    }

    public String getTitle()
    {
        return mTitle;
    }


   public String getUrl()
    {
        return mUrl;
    }

    public String getThumbnail()
    {
        return mThumbnail;
    }

    public String getPublished()
    {
        return mPublished;
    }
}
