package com.TwitterClone.ProjectBackend.Controller;

import com.TwitterClone.ProjectBackend.Model.Trend;
import com.TwitterClone.ProjectBackend.Service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping("/explore/trends")
    public String loadMoreTrends(Model model,
                                 @Param("from") int from,
                                 @Param("size") int size) {
        List<Trend> newTrends = this.hashtagService.getCurrentTrends(from, size);
        model.addAttribute("trends", newTrends);

        return "explore_element";
    }

}
