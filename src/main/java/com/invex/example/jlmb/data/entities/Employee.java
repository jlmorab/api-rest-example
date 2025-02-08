package com.invex.example.jlmb.data.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Employee", description = "Entidad que representa a un empleado")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empleado_id")
	@JsonProperty("empleado_id")
	@Schema(description = "Identificador único del empleado", example = "1")
	private Integer empleadoId;

	@NotBlank(message = "El primer nombre es requerido. No puede estar vacío")
	@Size(min = 2, max = 50, message = "El primer nombre debe tener entre 2 y 50 caracteres")
	@Column(name = "primer_nombre")
	@JsonProperty("primer_nombre")
	@Schema(description = "Primer nombre del empleado", example = "Juan")
	private String primerNombre;

	@Size(max = 50, message = "El segundo nombre no puede tener más de 50 caracteres")
	@Column(name = "segundo_nombre")
	@JsonProperty("segundo_nombre")
	@Schema(description = "Segundo nombre del empleado (opcional)", example = "Carlos")
	private String segundoNombre;

	@NotBlank(message = "El apellido paterno es requerido. No puede estar vacío")
	@Size(min = 2, max = 50, message = "El apellido paterno debe tener entre 2 y 50 caracteres")
	@Column(name = "apellido_paterno")
	@JsonProperty("apellido_paterno")
	@Schema(description = "Apellido paterno del empleado", example = "Pérez")
	private String apellidoPaterno;

	@NotBlank(message = "El apellido materno es requerido. No puede estar vacío")
	@Size(min = 2, max = 50, message = "El apellido materno debe tener entre 2 y 50 caracteres")
	@Column(name = "apellido_materno")
	@JsonProperty("apellido_materno")
	@Schema(description = "Apellido materno del empleado", example = "Gómez")
	private String apellidoMaterno;

	@NotNull(message = "La edad es requerida.")
	@Min(value = 18, message = "La edad debe ser al menos 18 años")
	@Max(value = 100, message = "La edad no puede superar los 100 años")
	@Column(name = "edad")
	@Schema(description = "Edad del empleado", example = "30", minimum = "18", maximum = "100")
	private Short edad;

	@NotNull(message = "El sexo es requerido")
	@Pattern(regexp = "M|F|X", message = "El sexo debe ser 'M', 'F' o 'X'")
	@Column(name = "sexo")
	@Schema(description = "Sexo del empleado (M: Masculino, F: Femenino, X: No especificado)", example = "M")
	private String sexo;

	@NotNull(message = "La fecha de nacimiento es requerida")
	@Past(message = "La fecha de nacimiento debe ser en el pasado")
	@Column(name = "fecha_nacimiento")
	@JsonProperty("fecha_nacimiento")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Schema(description = "Fecha de nacimiento del empleado (formato dd-MM-yyyy)", type = "string", pattern = "dd-MM-yyyy", example = "15-08-1990")
	private LocalDate fechaNacimiento;

	@NotBlank(message = "El puesto es requerido. No puede estar vacío")
	@Size(min = 2, max = 100, message = "El puesto debe tener entre 2 y 100 caracteres")
	@Column(name = "puesto")
	@Schema(description = "Puesto del empleado", example = "Ingeniero de software")
	private String puesto;

}
