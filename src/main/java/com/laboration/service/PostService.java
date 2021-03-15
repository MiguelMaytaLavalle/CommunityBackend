package com.laboration.service;

import com.laboration.DTO.PostWrapper;
import com.laboration.models.User;
import com.laboration.models.Post;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostService extends Services {
    @Transactional
    public PostWrapper createPost(PostWrapper post){
        if(post != null){
            Date date = Calendar.getInstance().getTime();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = format.format(date);
            post.setPostDate(strDate);
            postRepo.save(object.mapper.map(post, Post.class));
        }
        return post;
    }

    public List<PostWrapper> getPosts(String username){
        User user = userRepo.findByUsername(username);
        List<Post> posts = postRepo.findAllByUser(user);
        List<PostWrapper> postdto = new ArrayList<PostWrapper>();
        for(int i = 0; i < posts.size(); i++){
            postdto.add(object.mapper.map(posts.get(i), PostWrapper.class));
        }
        postdto.sort(new Comparator<PostWrapper>() {
            @Override
            public int compare(PostWrapper o1, PostWrapper o2) {
                return o2.getPostDate().compareTo(o1.getPostDate());
            }
        });
        return postdto;
    }
}
