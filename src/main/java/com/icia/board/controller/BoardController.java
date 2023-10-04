package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String boardSaveForm(){
        return "boardPages/boardSave";
    }
    @PostMapping("/save")
    public String boardSave(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "index";
    }

    @GetMapping
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return"boardPages/boardList";
    }

    @GetMapping ("/{id}")
    public String findById(@PathVariable("id") Long Id, Model model){
        try{
            BoardDTO boardDTO = boardService.findById(Id);
            boardDTO.setBoardHits(boardDTO.getBoardHits()+1);
            boardService.update(boardDTO);
            model.addAttribute("board", boardDTO);
            return "boardPages/boardDetail";
        }catch(NoSuchElementException e){
            return "boardPages/NotFound";
        }catch(Exception e){
            return "boardPages/NotFound";
        }
    }
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long Id, Model model){
        try{
            BoardDTO boardDTO = boardService.findById(Id);
            model.addAttribute("board", boardDTO);
            return "boardPages/boardUpdate";
        }catch
    }
}
