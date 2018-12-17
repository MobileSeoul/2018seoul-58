package com.walk.angel.angelwalk.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostData implements Serializable {

    private int mId;

    private String mAuthor;
    private String mTitle;
    private String mContent;

    public PostData(int id, String author, String title) {
        mId = id;
        mAuthor = author;
        mTitle = title;
    }
    public PostData(int id, String author, String title, String content) {
        mId = id;
        mAuthor = author;
        mTitle = title;
        mContent = content;
    }
    public PostData(String author, String title, String content) {
        mAuthor = author;
        mTitle = title;
        mContent = content;
    }
    public int getId() {
        return mId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }


    private static int lastContactId = 0;

    public static List<PostData> createPostList(int numContacts) {
        List<PostData> posts = new ArrayList<>();

        for (int i = 1; i <= numContacts; i++) {
            posts.add(new PostData(i,"작성자 " + i, "제목이다 " + i));
        }

        return posts;
    }
}
