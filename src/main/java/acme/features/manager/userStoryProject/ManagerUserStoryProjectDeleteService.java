
package acme.features.manager.userStoryProject;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.userStories.UserStory;
import acme.entities.userStories.UserStoryProject;
import acme.roles.Manager;

@Service
public class ManagerUserStoryProjectDeleteService extends AbstractService<Manager, UserStoryProject> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryProjectRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int managerId;
		UserStoryProject userStoryProject;

		id = super.getRequest().getData("id", int.class);
		userStoryProject = this.repository.findOneUserStoryProjectById(id);

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		status = managerId == userStoryProject.getProject().getManager().getId() && userStoryProject.getProject().isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStoryProject object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStoryProject object) {
		assert object != null;

		super.bind(object, "project", "userStory");
	}

	@Override
	public void validate(final UserStoryProject object) {
		assert object != null;

	}

	@Override
	public void perform(final UserStoryProject object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final UserStoryProject object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choicesProject;
		SelectChoices choicesUserStory;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		Collection<Project> projectsOwned = this.repository.findAllProjectsOwnedAndNotPublished(managerId);
		Collection<UserStory> userStoriesOwned = this.repository.findAllUserStoriesOwned(managerId);

		choicesProject = SelectChoices.from(projectsOwned, "code", object.getProject());
		choicesUserStory = SelectChoices.from(userStoriesOwned, "title", object.getUserStory());

		dataset = new Dataset();

		dataset.put("project", object.getProject().getCode());
		dataset.put("userStory", object.getUserStory().getTitle());
		dataset.put("projects", choicesProject);
		dataset.put("userStories", choicesUserStory);

		super.getResponse().addData(dataset);
	}
}
