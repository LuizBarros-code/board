package br.com.dio.util;

import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.persistence.entity.CardEntity;
import br.com.dio.persistence.entity.BoardColumnEntity;
import br.com.dio.persistence.entity.BoardColumnKindEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da classe ValidationUtil")
class ValidationUtilTest {

    private BoardEntity board;
    private CardEntity card;
    private BoardColumnEntity column;

    @BeforeEach
    void setUp() {
        // Setup Board
        board = new BoardEntity();
        board.setId(1L);
        board.setName("Board de Teste");
        
        // Setup Column
        column = new BoardColumnEntity();
        column.setId(1L);
        column.setName("A Fazer");
        column.setKind(BoardColumnKindEnum.INITIAL);
        column.setOrder(0);
        
        // Setup Card
        card = new CardEntity();
        card.setId(1L);
        card.setTitle("Card de Teste");
        card.setDescription("Descrição do card");
        card.setBoardColumn(column);
    }

    @Test
    @DisplayName("Deve validar board válido")
    void shouldValidateValidBoard() {
        // Given
        board.addColumn(column);
        
        // When & Then
        assertTrue(ValidationUtil.isValidBoard(board));
    }

    @Test
    @DisplayName("Deve falhar validação de board sem colunas")
    void shouldFailValidationForBoardWithoutColumns() {
        // When & Then
        assertFalse(ValidationUtil.isValidBoard(board));
    }

    @Test
    @DisplayName("Deve validar card válido")
    void shouldValidateValidCard() {
        // When & Then
        assertTrue(ValidationUtil.isValidCard(card));
    }

    @Test
    @DisplayName("Deve falhar validação de card sem coluna")
    void shouldFailValidationForCardWithoutColumn() {
        // Given
        card.setBoardColumn(null);
        
        // When & Then
        assertFalse(ValidationUtil.isValidCard(card));
    }

    @Test
    @DisplayName("Deve validar coluna válida")
    void shouldValidateValidColumn() {
        // When & Then
        assertTrue(ValidationUtil.isValidColumn(column));
    }

    @Test
    @DisplayName("Deve falhar validação de coluna sem tipo")
    void shouldFailValidationForColumnWithoutKind() {
        // Given
        column.setKind(null);
        
        // When & Then
        assertFalse(ValidationUtil.isValidColumn(column));
    }

    @Test
    @DisplayName("Deve falhar validação de coluna sem nome")
    void shouldFailValidationForColumnWithoutName() {
        // Given
        column.setName("");
        
        // When & Then
        assertFalse(ValidationUtil.isValidColumn(column));
    }

    @Test
    @DisplayName("Deve falhar validação de card sem título")
    void shouldFailValidationForCardWithoutTitle() {
        // Given
        card.setTitle("");
        
        // When & Then
        assertFalse(ValidationUtil.isValidCard(card));
    }

    @Test
    @DisplayName("Deve falhar validação de board com nome muito curto")
    void shouldFailValidationForBoardWithShortName() {
        // Given
        board.setName("AB");
        
        // When & Then
        assertFalse(ValidationUtil.isValid(board));
    }
}
