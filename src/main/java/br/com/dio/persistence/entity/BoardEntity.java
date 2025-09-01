package br.com.dio.persistence.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static br.com.dio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.dio.persistence.entity.BoardColumnKindEnum.INITIAL;

@Data
public class BoardEntity {

    private static final Logger logger = LoggerFactory.getLogger(BoardEntity.class);

    private Long id;
    
    @NotBlank(message = "O nome do board não pode estar vazio")
    @Size(min = 3, max = 100, message = "O nome do board deve ter entre 3 e 100 caracteres")
    private String name;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getInitialColumn(){
        logger.debug("Buscando coluna inicial do board {}", id);
        return getFilteredColumn(bc -> bc.getKind().equals(INITIAL));
    }

    public BoardColumnEntity getCancelColumn(){
        logger.debug("Buscando coluna de cancelamento do board {}", id);
        return getFilteredColumn(bc -> bc.getKind().equals(CANCEL));
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter){
        return boardColumns.stream()
                .filter(filter)
                .findFirst()
                .orElseThrow(() -> {
                    logger.error("Coluna não encontrada para o board {}", id);
                    return new IllegalStateException("Coluna não encontrada");
                });
    }
    
    public void addColumn(BoardColumnEntity column) {
        if (column != null) {
            boardColumns.add(column);
            logger.debug("Coluna {} adicionada ao board {}", column.getName(), id);
        }
    }
    
    public boolean isValid() {
        boolean hasInitial = boardColumns.stream().anyMatch(bc -> bc.getKind().equals(INITIAL));
        boolean hasCancel = boardColumns.stream().anyMatch(bc -> bc.getKind().equals(CANCEL));
        
        if (!hasInitial || !hasCancel) {
            logger.warn("Board {} inválido: deve ter colunas inicial e de cancelamento", id);
            return false;
        }
        
        return true;
    }
}
