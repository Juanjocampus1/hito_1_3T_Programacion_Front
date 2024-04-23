package DTO;

import java.math.BigDecimal;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DataDTO {
    private Long id;
    private String name;
    private String description;
    private int stock;
    private BigDecimal price;
    private String category;
    private String provider;
    private String FechaCreacion;
    private String FechaActualizacion;
    private Long idInform;
    
	public DataDTO(Long id, String name, String description, int stock, BigDecimal price, String category, String provider, String fechaCreacion, String fechaActualizacion, Long idInform) {
		
		super();
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.price = price;
		this.category = category;
		this.provider = provider;
		FechaCreacion = fechaCreacion;
		FechaActualizacion = fechaActualizacion;
		this.idInform = idInform;
	}

	public DataDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public String getFechaActualizacion() {
		return FechaActualizacion;
	}

	public void setFechaActualizacion(String fechaActualizacion) {
		FechaActualizacion = fechaActualizacion;
	}

	public Long getIdInform() {
		return idInform;
	}

	public void setIdInform(Long idInform) {
		this.idInform = idInform;
	}

	@Override
	public String toString() {
		return "DataDTO [id=" + id + ", name=" + name + ", description=" + description + ", stock=" + stock + ", price="
				+ price + ", category=" + category + ", provider=" + provider + ", FechaCreacion=" + FechaCreacion
				+ ", FechaActualizacion=" + FechaActualizacion + ", idInform=" + idInform + "]";
	}
    
}