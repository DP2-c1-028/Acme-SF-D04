
package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userStories.UserStory;
import acme.entities.userStories.UserStoryProject;
import acme.roles.Manager;

@Service
public class ManagerUserStoryDeleteService extends AbstractService<Manager, UserStory> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		int managerId;
		UserStory userStory;

		id = super.getRequest().getData("id", int.class);
		userStory = this.repository.findOneUserStoryById(id);

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		status = managerId == userStory.getManager().getId() && userStory.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "estimatedCost", "priority", "link", "acceptanceCriteria");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		Collection<UserStoryProject> relations = this.repository.findAllRelationsByUserStoryId(object.getId());

		this.repository.deleteAll(relations);

		this.repository.delete(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimatedCost", "priority", "link", "acceptanceCriteria", "draftMode");

		super.getResponse().addData(dataset);
	}
}
