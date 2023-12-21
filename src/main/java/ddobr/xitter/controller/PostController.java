package ddobr.xitter.controller;

import ddobr.xitter.dto.LikeDTO;
import ddobr.xitter.dto.PostDTO;
import ddobr.xitter.dto.UserDTO;
import ddobr.xitter.entity.Like;
import ddobr.xitter.entity.LikeKey;
import ddobr.xitter.entity.Post;
import ddobr.xitter.entity.User;
import ddobr.xitter.repository.LikeRepository;
import ddobr.xitter.repository.PostRepository;
import ddobr.xitter.repository.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @GetMapping
    public String index(Model model, @Nullable @CookieValue("userid") String useridCookie) {
        if (useridCookie == null) {
            return "redirect:/auth";
        }

        model.addAttribute("userId", useridCookie);

        List<Post> allPosts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : allPosts) {
            PostDTO postDTO = new PostDTO();
            postDTO.setText(post.getText());
            postDTO.setAuthorId(post.getAuthor().getId());

            Optional<User> author = userRepository.findById(post.getAuthor().getId());
            postDTO.setAuthor(author.get());

            postDTO.setId(post.getId());
            Like like = likeRepository.findLikeByPostIdAndSenderId(post.getId(), Long.parseLong(useridCookie));
            postDTO.setLikesAmount(likeRepository.findByPostId(post.getId()).size());
            if (like != null) {
                postDTO.setLiked(true);
            } else {
                postDTO.setLiked(false);
            }

            postDTOs.add(postDTO);
        }


        model.addAttribute("posts", postDTOs);

        return "posts";
    }

    @PostMapping
    public String add(@Nullable @CookieValue("userid") String useridCookie, @Valid PostDTO request) {
        if (useridCookie == null) {
            return "redirect:/auth";
        }

        Optional<User> user = userRepository.findById(request.getAuthorId());

        if (user.isPresent()) {
            Post post = request.toEntity();
            post.setAuthor(user.get());
            postRepository.save(post);
        }

        return "redirect:/posts";
    }

    @PostMapping("/like")
    public String like(@Nullable @CookieValue("userid") String useridCookie, @Positive Long postId) {
        if (useridCookie == null) {
            return "redirect:/auth";
        }

        Optional<User> user = userRepository.findById(Long.parseLong(useridCookie));
        Optional<Post> post = postRepository.findById(postId);


        if (user.isPresent() && post.isPresent()) {
            Like like = likeRepository.findLikeByPostIdAndSenderId(post.get().getId(), user.get().getId());

            if (like != null) {
                likeRepository.delete(like);
            } else {
                LikeKey likeKey = new LikeKey();
                likeKey.setPostId(postId);
                likeKey.setSenderId(Long.parseLong(useridCookie));

                Like newLike = new Like();
                newLike.setId(likeKey);
                newLike.setPost(post.get());
                newLike.setSender(user.get());

                likeRepository.save(newLike);
            }
        }

        return "redirect:/posts";
    }
}
