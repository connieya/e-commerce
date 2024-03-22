package com.example.hanghaeplus.presentation.point;

import com.example.hanghaeplus.application.point.PointLineService;
import com.example.hanghaeplus.domain.point.PointLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point-line")
public class PointLineFormController {

    private final PointLineService pointLineService;
    @GetMapping("/all")
    public String all(Model model){
        List<PointLine> pointLines = pointLineService.findAll();
        return "point_line/all";
    }
}
