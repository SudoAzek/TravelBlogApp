package com.mezonworks.travelblog.repository;

import com.mezonworks.travelblog.http.Blog;

import java.util.List;

public interface DataFromDatabaseCallback {
    void onSuccess(List<Blog> blogList);
}
