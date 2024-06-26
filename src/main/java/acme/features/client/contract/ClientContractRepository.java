
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contracts.Contract;
import acme.entities.progress_logs.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.roles.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("select c from Contract c where c.client.id = :id")
	Collection<Contract> findContractsByClientId(int id);

	@Query("select c from Contract c where c.id = :id")
	Contract findContractById(int id);

	@Query("select c from Contract c where c.code = :code")
	Contract findContractByCode(String code);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findContractsByProjectId(int projectId);

	@Query("select c from Contract c where c.draftMode = false and c.project.id = :projectId ")
	Collection<Contract> findPublishedContractsByProjectId(int projectId);

	@Query("select p from Project p")
	Collection<Project> findlAllProjects();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findlAllPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select c from Client c where c.id = :id")
	Client findClientById(int id);

	@Query("select p from ProgressLog p where p.contract.id = :id")
	Collection<ProgressLog> findProgressLogsByContractId(int id);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();

}
