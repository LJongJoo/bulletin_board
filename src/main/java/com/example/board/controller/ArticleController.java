package com.example.board.controller;

import com.example.board.articleRepository.ArticleRepository;
import com.example.board.dto.ArticleForm;
import com.example.board.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String ArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString()); //form에 데이터가 잘 들어오는지 확인
        Article articleEntity = form.toEntity(); // DTO를 엔티티로 전환
        log.info(articleEntity.toString());// 엔티티로 잘 전환 되었는지 확인
        Article saved = articleRepository.save(articleEntity); //변환된 엔티티를 리포지토리를 이용해 데이터베이스에 저장
        log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article",articleEntity);
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        List<Article> articleList = (ArrayList<Article>)articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "articles/index";
    }

}
