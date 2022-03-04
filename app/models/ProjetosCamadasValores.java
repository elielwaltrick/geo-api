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
@Table(schema = "vm2_view", name = "projetos_camadas_valores")
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class ProjetosCamadasValores extends GenericObject {
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Id
	@Column(name = "id_projetos_camadas_valores", columnDefinition = "serial")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_projetos")
	private Projetos projeto;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas")
	private ProjetosCamadas projetoCamada;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas_formularios_campos")
	private ProjetosCamadasFormulariosCampos projetoCamadaFormularioCampo;

	@ManyToOne
	@JoinColumn(name = "id_projetos_camadas_geometrias")
	private ProjetosCamadasGeometrias projetoCamadaGeometria;

	public Integer getId() {
		return id;
	}

	public Projetos getProjeto() {
		return projeto;
	}

	public ProjetosCamadas getProjetoCamada() {
		return projetoCamada;
	}

	public ProjetosCamadasFormulariosCampos getProjetoCamadaFormularioCampo() {
		return projetoCamadaFormularioCampo;
	}

	public ProjetosCamadasGeometrias getProjetoCamadaGeometria() {
		return projetoCamadaGeometria;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProjeto(Projetos projeto) {
		this.projeto = projeto;
	}

	public void setProjetoCamada(ProjetosCamadas projetoCamada) {
		this.projetoCamada = projetoCamada;
	}

	public void setProjetoCamadaFormularioCampo(ProjetosCamadasFormulariosCampos projetoCamadaFormularioCampo) {
		this.projetoCamadaFormularioCampo = projetoCamadaFormularioCampo;
	}

	public void setProjetoCamadaGeometria(ProjetosCamadasGeometrias projetoCamadaGeometria) {
		this.projetoCamadaGeometria = projetoCamadaGeometria;
	}

}
