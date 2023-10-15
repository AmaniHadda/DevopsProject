package tn.esprit.devops_project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	Long idSupplier;
	@NonNull
	String code;
	@NonNull
	String label;
	@Enumerated(EnumType.STRING)
	@NonNull
	SupplierCategory supplierCategory;
	@OneToMany(mappedBy="supplier")
	@JsonIgnore
	List<Invoice> invoices= new ArrayList<>();
	@ManyToMany
	@JsonIgnore
	private Set<ActivitySector> activitySectors;



}