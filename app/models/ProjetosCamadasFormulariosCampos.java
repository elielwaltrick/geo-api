package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(schema = "vm2_view", name = "projetos_camadas_formularios_campos")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ProjetosCamadasFormulariosCampos extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_projetos_camadas_formularios_campos", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "tipo")
	private Integer tipo;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas_formularios")
	private ProjetosCamadasFormularios projetoCamadaFormulario;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public ProjetosCamadasFormularios getProjetoCamadaFormulario() {
		return projetoCamadaFormulario;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setProjetoCamadaFormulario(ProjetosCamadasFormularios projetoCamadaFormulario) {
		this.projetoCamadaFormulario = projetoCamadaFormulario;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}
