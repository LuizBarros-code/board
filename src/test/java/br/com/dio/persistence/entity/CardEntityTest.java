package br.com.dio.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade CardEntity")
class CardEntityTest {

    private CardEntity card;
    private BoardColumnEntity column;

    @BeforeEach
    void setUp() {
        card = new CardEntity();
        card.setId(1L);
        card.setTitle("Card de Teste");
        card.setDescription("Descrição do card de teste");
        
        column = new BoardColumnEntity();
        column.setId(1L);
        column.setName("A Fazer");
        column.setKind(BoardColumnKindEnum.INITIAL);
        
        card.setBoardColumn(column);
    }

    @Test
    @DisplayName("Deve criar um card válido")
    void shouldCreateValidCard() {
        // Given & When
        CardEntity newCard = new CardEntity();
        newCard.setTitle("Novo Card");
        newCard.setDescription("Descrição do novo card");
        
        // Then
        assertNotNull(newCard);
        assertEquals("Novo Card", newCard.getTitle());
        assertEquals("Descrição do novo card", newCard.getDescription());
    }

    @Test
    @DisplayName("Deve definir título com trim automático")
    void shouldSetTitleWithAutoTrim() {
        // Given
        String titleWithSpaces = "  Título com Espaços  ";
        
        // When
        card.setTitle(titleWithSpaces);
        
        // Then
        assertEquals("Título com Espaços", card.getTitle());
    }

    @Test
    @DisplayName("Deve definir descrição com trim automático")
    void shouldSetDescriptionWithAutoTrim() {
        // Given
        String descriptionWithSpaces = "  Descrição com Espaços  ";
        
        // When
        card.setDescription(descriptionWithSpaces);
        
        // Then
        assertEquals("Descrição com Espaços", card.getDescription());
    }

    @Test
    @DisplayName("Deve bloquear card com motivo")
    void shouldBlockCardWithReason() {
        // Given
        String blockReason = "Card em análise";
        
        // When
        card.block(blockReason);
        
        // Then
        assertTrue(card.isBlocked());
        assertEquals(blockReason, card.getBlockReason());
        assertNotNull(card.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve desbloquear card com motivo")
    void shouldUnblockCardWithReason() {
        // Given
        card.block("Motivo de bloqueio");
        
        // When
        card.unblock("Motivo de desbloqueio");
        
        // Then
        assertFalse(card.isBlocked());
        assertNull(card.getBlockReason());
        assertNotNull(card.getUpdatedAt());
    }

    @Test
    @DisplayName("Deve permitir movimento quando card não está bloqueado")
    void shouldAllowMovementWhenCardNotBlocked() {
        // When & Then
        assertTrue(card.canMove());
    }

    @Test
    @DisplayName("Não deve permitir movimento quando card está bloqueado")
    void shouldNotAllowMovementWhenCardBlocked() {
        // Given
        card.block("Card bloqueado");
        
        // When & Then
        assertFalse(card.canMove());
    }

    @Test
    @DisplayName("Deve ignorar título nulo ou vazio")
    void shouldIgnoreNullOrEmptyTitle() {
        // Given
        String originalTitle = card.getTitle();
        
        // When
        card.setTitle(null);
        card.setTitle("");
        card.setTitle("   ");
        
        // Then
        assertEquals(originalTitle, card.getTitle());
    }

    @Test
    @DisplayName("Deve ignorar descrição nula")
    void shouldIgnoreNullDescription() {
        // Given
        String originalDescription = card.getDescription();
        
        // When
        card.setDescription(null);
        
        // Then
        assertEquals(originalDescription, card.getDescription());
    }
}
