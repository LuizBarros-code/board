package br.com.dio.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
public class BoardColumnEntity {

    private static final Logger logger = LoggerFactory.getLogger(BoardColumnEntity.class);

    private Long id;
    
    @NotBlank(message = "O nome da coluna não pode estar vazio")
    @Size(min = 2, max = 50, message = "O nome da coluna deve ter entre 2 e 50 caracteres")
    private String name;
    
    private int order;
    
    @NotNull(message = "O tipo da coluna não pode ser nulo")
    private BoardColumnKindEnum kind;
    
    private BoardEntity board = new BoardEntity();
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<CardEntity> cards = new ArrayList<>();

    public void addCard(CardEntity card) {
        if (card != null) {
            cards.add(card);
            card.setBoardColumn(this);
            logger.debug("Card {} adicionado à coluna {}", card.getId(), id);
        }
    }
    
    public void removeCard(CardEntity card) {
        if (card != null && cards.remove(card)) {
            logger.debug("Card {} removido da coluna {}", card.getId(), id);
        }
    }
    
    public boolean isFull() {
        int maxCards = 100; // Configurável
        boolean full = cards.size() >= maxCards;
        if (full) {
            logger.warn("Coluna {} está cheia ({} cards)", id, cards.size());
        }
        return full;
    }
    
    public int getCardCount() {
        return cards.size();
    }
}
