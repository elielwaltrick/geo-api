package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(schema = "vm2_view", name = "projetos_camadas_formularios")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ProjetosCamadasFormularios extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_projetos_camadas_formularios", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome")
	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas")
	private ProjetosCamadas projetoCamada;

	@OneToMany(mappedBy = "projetoCamadaFormulario", fetch = FetchType.EAGER)
	private List<ProjetosCamadasFormulariosCampos> projetosCamadasFormulariosCampos;

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public ProjetosCamadas getProjetoCamada() {
		return projetoCamada;
	}

	public List<ProjetosCamadasFormulariosCampos> getProjetosCamadasFormulariosCampos() {
		return projetosCamadasFormulariosCampos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setProjetoCamada(ProjetosCamadas projetoCamada) {
		this.projetoCamada = projetoCamada;
	}

	public void setProjetosCamadasFormulariosCampos(List<ProjetosCamadasFormulariosCampos> projetosCamadasFormulariosCampos) {
		this.projetosCamadasFormulariosCampos = projetosCamadasFormulariosCampos;
	}

}
