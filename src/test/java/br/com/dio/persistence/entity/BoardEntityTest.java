package br.com.dio.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade BoardEntity")
class BoardEntityTest {

    private BoardEntity board;
    private BoardColumnEntity initialColumn;
    private BoardColumnEntity pendingColumn;
    private BoardColumnEntity finalColumn;
    private BoardColumnEntity cancelColumn;

    @BeforeEach
    void setUp() {
        board = new BoardEntity();
        board.setId(1L);
        board.setName("Board de Teste");
        
        // Criar colunas padrão
        initialColumn = new BoardColumnEntity();
        initialColumn.setId(1L);
        initialColumn.setName("A Fazer");
        initialColumn.setKind(BoardColumnKindEnum.INITIAL);
        initialColumn.setOrder(0);
        
        pendingColumn = new BoardColumnEntity();
        pendingColumn.setId(2L);
        pendingColumn.setName("Em Andamento");
        pendingColumn.setKind(BoardColumnKindEnum.PENDING);
        pendingColumn.setOrder(1);
        
        finalColumn = new BoardColumnEntity();
        finalColumn.setId(3L);
        finalColumn.setName("Concluído");
        finalColumn.setKind(BoardColumnKindEnum.FINAL);
        finalColumn.setOrder(2);
        
        cancelColumn = new BoardColumnEntity();
        cancelColumn.setId(4L);
        cancelColumn.setName("Cancelado");
        cancelColumn.setKind(BoardColumnKindEnum.CANCEL);
        cancelColumn.setOrder(3);
    }

    @Test
    @DisplayName("Deve criar um board válido com colunas padrão")
    void shouldCreateValidBoardWithDefaultColumns() {
        // Given
        board.addColumn(initialColumn);
        board.addColumn(pendingColumn);
        board.addColumn(finalColumn);
        board.addColumn(cancelColumn);
        
        // When & Then
        assertTrue(board.isValid());
        assertEquals(4, board.getBoardColumns().size());
        assertEquals("A Fazer", board.getInitialColumn().getName());
        assertEquals("Cancelado", board.getCancelColumn().getName());
    }

    @Test
    @DisplayName("Deve falhar validação quando board não tem coluna inicial")
    void shouldFailValidationWhenBoardHasNoInitialColumn() {
        // Given
        board.addColumn(pendingColumn);
        board.addColumn(finalColumn);
        board.addColumn(cancelColumn);
        
        // When & Then
        assertFalse(board.isValid());
    }

    @Test
    @DisplayName("Deve falhar validação quando board não tem coluna de cancelamento")
    void shouldFailValidationWhenBoardHasNoCancelColumn() {
        // Given
        board.addColumn(initialColumn);
        board.addColumn(pendingColumn);
        board.addColumn(finalColumn);
        
        // When & Then
        assertFalse(board.isValid());
    }

    @Test
    @DisplayName("Deve adicionar coluna ao board")
    void shouldAddColumnToBoard() {
        // Given
        BoardColumnEntity newColumn = new BoardColumnEntity();
        newColumn.setName("Nova Coluna");
        newColumn.setKind(BoardColumnKindEnum.PENDING);
        
        // When
        board.addColumn(newColumn);
        
        // Then
        assertEquals(1, board.getBoardColumns().size());
        assertTrue(board.getBoardColumns().contains(newColumn));
    }

    @Test
    @DisplayName("Deve retornar coluna inicial corretamente")
    void shouldReturnInitialColumnCorrectly() {
        // Given
        board.addColumn(initialColumn);
        
        // When
        BoardColumnEntity result = board.getInitialColumn();
        
        // Then
        assertNotNull(result);
        assertEquals(BoardColumnKindEnum.INITIAL, result.getKind());
        assertEquals("A Fazer", result.getName());
    }

    @Test
    @DisplayName("Deve retornar coluna de cancelamento corretamente")
    void shouldReturnCancelColumnCorrectly() {
        // Given
        board.addColumn(cancelColumn);
        
        // When
        BoardColumnEntity result = board.getCancelColumn();
        
        // Then
        assertNotNull(result);
        assertEquals(BoardColumnKindEnum.CANCEL, result.getKind());
        assertEquals("Cancelado", result.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção quando coluna não é encontrada")
    void shouldThrowExceptionWhenColumnNotFound() {
        // Given
        board.addColumn(initialColumn);
        
        // When & Then
        assertThrows(IllegalStateException.class, () -> board.getCancelColumn());
    }
}
