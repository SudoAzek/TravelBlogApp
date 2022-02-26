package com.mezonworks.travelblog.repository;

import com.mezonworks.travelblog.http.Blog;

import java.util.List;

public interface DataFromNetworkCallback {
    void onSuccess(List<Blog> blogList);
    void onError();
}
