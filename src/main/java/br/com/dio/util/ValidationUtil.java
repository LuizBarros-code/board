package br.com.dio.util;

import br.com.dio.persistence.entity.BoardEntity;
import br.com.dio.persistence.entity.CardEntity;
import br.com.dio.persistence.entity.BoardColumnEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class ValidationUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    public static <T> boolean isValid(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        
        if (!violations.isEmpty()) {
            logger.warn("Entidade {} tem {} violações de validação", 
                entity.getClass().getSimpleName(), violations.size());
            violations.forEach(violation -> 
                logger.warn("Campo '{}': {}", violation.getPropertyPath(), violation.getMessage()));
            return false;
        }
        
        return true;
    }
    
    public static boolean isValidBoard(BoardEntity board) {
        if (!isValid(board)) {
            return false;
        }
        
        if (board.getBoardColumns() == null || board.getBoardColumns().isEmpty()) {
            logger.warn("Board deve ter pelo menos uma coluna");
            return false;
        }
        
        return true;
    }
    
    public static boolean isValidCard(CardEntity card) {
        if (!isValid(card)) {
            return false;
        }
        
        if (card.getBoardColumn() == null) {
            logger.warn("Card deve estar associado a uma coluna");
            return false;
        }
        
        return true;
    }
    
    public static boolean isValidColumn(BoardColumnEntity column) {
        if (!isValid(column)) {
            return false;
        }
        
        if (column.getKind() == null) {
            logger.warn("Coluna deve ter um tipo definido");
            return false;
        }
        
        return true;
    }
}
