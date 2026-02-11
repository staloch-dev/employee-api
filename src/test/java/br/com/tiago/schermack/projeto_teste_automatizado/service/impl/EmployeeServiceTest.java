package br.com.tiago.schermack.projeto_teste_automatizado.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeRequestDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.dto.EmployeeResponseDTO;
import br.com.tiago.schermack.projeto_teste_automatizado.entity.Employee;
import br.com.tiago.schermack.projeto_teste_automatizado.repository.EmployeeRepository;

@SpringBootTest
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Deve criar um funcionário e retornar o EmployeeResponseDTO com sucesso")
    public void shouldCreateEmployeeAndReturnResponseDTOSuccessfully() {

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Jaison", "jaison@email.com");
        Employee employeeToSave = new Employee(requestDTO.firstName(), requestDTO.email());
        employeeToSave.setId(1L);

        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employeeToSave);

        // Act
        EmployeeResponseDTO responseDTO = employeeService.create(requestDTO);

        // Assert
        assertEquals(1L, responseDTO.id());
        assertEquals("Jaison", responseDTO.firstName());
        assertEquals("jaison@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Deve atualizar um funcionário e retornar o EmployeeResponseDTO com sucesso")
    public void shouldUpdateEmployeeAndReturnResponseDTOSuccessfully() {

        // Arrange
        EmployeeRequestDTO requestDTO = new EmployeeRequestDTO("Jaison Atualizado", "jaison.novo@email.com");
        Employee existingEmployee = new Employee("Jaison", "jaison@email.com");
        existingEmployee.setId(1L);

        when(employeeRepository.findById(existingEmployee.getId()))
                .thenReturn(Optional.of(existingEmployee));

        // Act
        EmployeeResponseDTO responseDTO = employeeService.update(existingEmployee.getId(), requestDTO);

        // Assert
        assertEquals(existingEmployee.getId(), responseDTO.id());
        assertEquals("Jaison Atualizado", responseDTO.firstName());
        assertEquals("jaison.novo@email.com", responseDTO.email());

        verify(employeeRepository, times(1)).findById(existingEmployee.getId());
    }

}
