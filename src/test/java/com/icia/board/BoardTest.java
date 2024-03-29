package com.icia.board;

import com.icia.board.dto.BoardDTO;
import com.icia.entity.BoardEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    //게시글 50개 저장하기
    private BoardDTO newBoard(int i){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardTitle("test" + i);
        boardDTO.setBoardContents("content" + i);
        boardDTO.setBoardPass("pass" + i);
        boardDTO.setBoardWriter("Writer" + i);
        return boardDTO;
    }

    @Test
    @DisplayName("게시글 데이터 50개")
    public void boardData(){
        IntStream.rangeClosed(1,50).forEach(i ->{
            boardService.save(newBoard(i));
        });
    }

    @Test
    @DisplayName("페이징 객체 확인")
    public void pagingMethod(){
        int page = 1;
        int pageLimit = 5; // 1page 당 보여지는 요소의 개수
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막페이지인지 여부

        // Page<BoardEntity> -> Page<BoardDTO>

        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
            BoardDTO.builder()
                    .id(boardEntity.getId())
                    .boardContents(boardEntity.getBoardContents())
                    .boardHits(boardEntity.getBoardHits())
                    .boardWriter(boardEntity.getBoardWriter())
                    .boardPass(boardEntity.getBoardPass())
                    .boardTitle(boardEntity.getBoardTitle())
                    .build());
            System.out.println("boardList.getContent() = " + boardList.getContent()); // 요청페이지에 들어있는 데이터
            System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 전체 글갯수
            System.out.println("boardList.getNumber() = " + boardList.getNumber()); // 요청페이지(jpa 기준)
            System.out.println("boardList.getTotalPages() = " + boardList.getTotalPages()); // 전체 페이지 갯수
            System.out.println("boardList.getSize() = " + boardList.getSize()); // 한페이지에 보여지는 글갯수
            System.out.println("boardList.hasPrevious() = " + boardList.hasPrevious()); // 이전페이지 존재 여부
            System.out.println("boardList.isFirst() = " + boardList.isFirst()); // 첫페이지인지 여부
            System.out.println("boardList.isLast() = " + boardList.isLast()); // 마지막페이지인지 여부

    }
}
