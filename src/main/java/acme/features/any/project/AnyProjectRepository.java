
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.entities.training_modules.TrainingModule;
import acme.entities.userStories.UserStory;
import acme.entities.userStories.UserStoryProject;
import acme.roles.Manager;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findProjectsPublished();

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select m from Manager m where m.id = :id")
	Manager findOneManagerById(int id);

	@Query("select usp from UserStoryProject usp where usp.project.id=:projectId")
	Collection<UserStoryProject> findRelationsByProjectId(int projectId);

	@Query("select tm from TrainingModule tm where tm.project.id=:projectId")
	Collection<TrainingModule> findTrainingModuleByProjectId(int projectId);

	@Query("select usp.userStory from UserStoryProject usp where usp.project.id=:projectId")
	Collection<UserStory> findUserStoryByProjectId(int projectId);

	@Query("select p from Project p where p.code=:code")
	Project findOneProjectByCode(String code);

	@Query("select sc from SystemConfiguration sc")
	SystemConfiguration findSystemConfiguration();
}
