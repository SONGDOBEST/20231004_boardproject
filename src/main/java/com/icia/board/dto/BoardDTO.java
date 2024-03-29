package com.icia.board.dto;


import com.icia.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {

    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private int boardHits;

    public static BoardDTO toDTO(BoardEntity boardEntity){
//        BoardDTO boardDTO = new BoardDTO();
//        boardDTO.setId(boardEntity.getId());
//        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
//        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
//        boardDTO.setBoardPass(boardEntity.getBoardPass());
//        boardDTO.setBoardContents(boardEntity.getBoardContents());
//        boardDTO.setBoardHits(boardEntity.getBoardHits());
        BoardDTO boardDTO = BoardDTO.builder()
                .id(boardEntity.getId())
                .boardWriter(boardEntity.getBoardWriter())
                .boardContents(boardEntity.getBoardContents())
                .boardHits(boardEntity.getBoardHits())
                .boardPass(boardEntity.getBoardPass())
                .boardTitle(boardEntity.getBoardTitle())
                .build();

        return boardDTO;
    }
}
