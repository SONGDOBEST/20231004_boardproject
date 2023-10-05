package com.icia.board.controller;

import com.icia.board.dto.BoardDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    /*
       rest api
       /board/10 => 10번글
       /board/25 => 25번글
       3페이지의 15번글
       /board/3/15
       /board/15?page=3
       */
    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        Page<BoardDTO> boardDTOList = boardService.findAll(page);
        model.addAttribute("boardList", boardDTOList);
        // 목록 하단에 보여줄 페이지 번호
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage =
                ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();
//        if ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()) {
//            endPage = startPage + blockLimit - 1;
//        } else {
//            endPage = boardDTOS.getTotalPages();
//        }
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardPages/boardList";
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
    public String updateForm(@PathVariable("id") Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardUpdate";
    }
//    @PostMapping("/update/{id}")
//    public String update(@PathVariable("id") Long id, @ModelAttribute BoardDTO boardDTO){
//        boardService.update(boardDTO);
//        return "redirect:/board";
//    }
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO){
        boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //주소로 요청
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable("id") Long id){
//        boardService.delete(id);
//        return "redirect:/board";
//    }

    //axios로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByAxios(@PathVariable("id") Long id){
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
