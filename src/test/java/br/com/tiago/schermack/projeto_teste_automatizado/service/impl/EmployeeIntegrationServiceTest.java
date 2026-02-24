package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
public class EmployeeIntegrationServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DisplayName("Deve criar um funcionário e retornar o EmployeeResponseDTO com sucesso")
    public void shouldCreateEmployeeAndReturnResponseDTOSuccessfully() {

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Jaison", "jaison@email.com");

        // Act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        // Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Jaison", responseDTO.firstName());
        assertEquals("jaison@email.com", responseDTO.email());
    }

    @Test
    @DisplayName("Deve atualizar um funcionário e retornar o EmployeeResponseDTO com sucesso")
    public void shouldUpdateEmployeeAndReturnResponseDTOSuccessfully() {

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Jaison Atualizado", "jaison_atualizado@email.com");
        EmployeeResponseDTO createdEmployee = employeeService.create(new EmployeeRequestDTO("Jaison", "jaison@email.com"));

        // Act
        EmployeeResponseDTO responseDTO = employeeService.update(createdEmployee.id(), requestDTO);

        // Assert
        assertEquals(createdEmployee.id(), responseDTO.id());
        assertEquals("Jaison Atualizado", responseDTO.firstName());
        assertEquals("jaison_atualizado@email.com", responseDTO.email());
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar atualizar um funcionário inexistente")
    public void shouldThrowEntityNotFoundExceptionWhenUpdatingNonExistentEmployee() {

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Jaison Atualizado", "jaison_atualizado@gmail.com");

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> employeeService.update(1L, requestDTO));
    }

    @Test
    @DisplayName("Deve deletar um funcionário com sucesso")
    public void shouldDeleteEmployeeSuccessfully() {

        // Arrange
        EmployeeResponseDTO createdEmployee = employeeService.create(new EmployeeRequestDTO("Jaison", "jaison@email.com"));

        // Act
        employeeService.delete(createdEmployee.id());

        // Assert
        assertThrows(EntityNotFoundException.class,
                () -> employeeService.findById(createdEmployee.id()));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException ao tentar deletar um funcionário inexistente")
    public void shouldThrowEntityNotFoundExceptionWhenDeletingNonExistentEmployee() {

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> employeeService.delete(1L));
    }

}
