package gq.jackg.example.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gq.jackg.example.model.Board;
import gq.jackg.example.web.services.BoardService;

@RestController("/board")
public class BoardController {
	@Autowired BoardService boardService;
	
	@GetMapping("/board")
	public Board getBoard(@RequestParam(name = "boardId", required = false) String boardId) {
		return boardService.getArticle(boardId);
	}
	
	@GetMapping("/board/list")
	public Map<String, Object> getTenArticle(
			@RequestParam(name = "isNext", required = true) Boolean isNext,
			@RequestParam(name = "startCursor", required = false) String startCursor,
			@RequestParam(name = "offset", required = false, defaultValue = "1") int offset) {
		
		if (isNext) {
			return boardService.getTen(startCursor);
		} else {
			return boardService.backWord(offset - 1);
		}
	}
	
	@PutMapping("/board")
	public Board writeBoard(Board board) {
		return boardService.writeArticle(board);
	}
	
	@PostMapping("/board")
	public Board updateBoard(Board board) {
		return boardService.updateArticle(board);
	}
	
	@DeleteMapping("/board")
	public void deleteBoard(@RequestParam(name = "boardId", required = true) Long boardId) {
		boardService.deleteArticle(boardId);
	}
}
