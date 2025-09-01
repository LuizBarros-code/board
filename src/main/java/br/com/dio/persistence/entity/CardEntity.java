package br.com.dio.persistence.entity;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
public class CardEntity {

    private static final Logger logger = LoggerFactory.getLogger(CardEntity.class);

    private Long id;
    
    @NotBlank(message = "O título do card não pode estar vazio")
    @Size(min = 3, max = 100, message = "O título do card deve ter entre 3 e 100 caracteres")
    private String title;
    
    @NotBlank(message = "A descrição do card não pode estar vazia")
    @Size(max = 500, message = "A descrição do card deve ter no máximo 500 caracteres")
    private String description;
    
    private BoardColumnEntity boardColumn = new BoardColumnEntity();
    
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean blocked = false;
    private String blockReason;
    
    public void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title.trim();
            logger.debug("Título do card {} definido como: {}", id, this.title);
        }
    }
    
    public void setDescription(String description) {
        if (description != null) {
            this.description = description.trim();
            logger.debug("Descrição do card {} atualizada", id);
        }
    }
    
    public void block(String reason) {
        this.blocked = true;
        this.blockReason = reason;
        this.updatedAt = OffsetDateTime.now();
        logger.info("Card {} bloqueado. Motivo: {}", id, reason);
    }
    
    public void unblock(String reason) {
        this.blocked = false;
        this.blockReason = null;
        this.updatedAt = OffsetDateTime.now();
        logger.info("Card {} desbloqueado. Motivo: {}", id, reason);
    }
    
    public boolean canMove() {
        if (blocked) {
            logger.warn("Card {} não pode ser movido pois está bloqueado", id);
            return false;
        }
        return true;
    }
}
